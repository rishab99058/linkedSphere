import 'dart:async';

import 'package:dio/dio.dart';
import 'package:mobile/config/appConfig.dart';
import 'package:mobile/network/apiEndpoints.dart';
import 'package:mobile/storage/secure_storage.dart';

class ApiClient {
  final Dio _dio;
  final Dio _refreshDio;

  Completer<String?>? _refreshCompleter;

  ApiClient()
    : _dio = Dio(
        BaseOptions(
          baseUrl: AppConfig.baseUrl,
          connectTimeout: const Duration(seconds: 10),
          receiveTimeout: const Duration(seconds: 10),
          sendTimeout: const Duration(seconds: 10),
          headers: {'Accept': 'application/json'},
        ),
      ),
      _refreshDio = Dio(
        BaseOptions(
          baseUrl: AppConfig.baseUrl,
          connectTimeout: const Duration(seconds: 10),
          receiveTimeout: const Duration(seconds: 10),
          sendTimeout: const Duration(seconds: 10),
          headers: {'Accept': 'application/json'},
        ),
      ) {
    _dio.interceptors.add(
      InterceptorsWrapper(
        // ==========================================================
        // REQUEST
        // ==========================================================
        onRequest: (options, handler) async {
          final isPublic = _isPublicEndpoint(options.path);
          final bool requiresAuth = options.extra['requiresAuth'] ?? !isPublic;

          if (!requiresAuth) {
            // Public endpoints (e.g. login, register, forgot-password) must NOT have auth header
            options.headers.remove('Authorization');
            return handler.next(options);
          }

          // Protected endpoints: attach access token if available
          if (!options.headers.containsKey('Authorization')) {
            final accessToken = await SecureStorage.getAccessToken();
            if (accessToken != null && accessToken.isNotEmpty) {
              options.headers['Authorization'] = 'Bearer $accessToken';
            }
          }

          handler.next(options);
        },

        // ==========================================================
        // ERROR
        // ==========================================================
        onError: (error, handler) async {
          final path = error.requestOptions.path;
          final isPublic = _isPublicEndpoint(path);
          final bool requiresAuth =
              error.requestOptions.extra['requiresAuth'] ?? !isPublic;

          final statusCode = error.response?.statusCode;

          // Only handle token refresh for protected endpoints returning 401 or 403
          if (!requiresAuth || (statusCode != 401 && statusCode != 403)) {
            handler.next(error);
            return;
          }

          // Don't retry same request again
          if (error.requestOptions.extra['retried'] == true) {
            handler.next(error);
            return;
          }

          // Refresh token
          final newAccessToken = await _refreshAccessToken();

          // Refresh failed
          if (newAccessToken == null) {
            await SecureStorage.clearAccessToken();
            await SecureStorage.clearRefreshToken();
            await SecureStorage.saveIsLoggedIn(false);

            handler.next(error);
            return;
          }

          // Mark request as retried
          error.requestOptions.extra['retried'] = true;

          // Set new token
          error.requestOptions.headers['Authorization'] =
              'Bearer $newAccessToken';

          // Retry original request
          try {
            final response = await _dio.fetch(error.requestOptions);
            handler.resolve(response);
          } catch (e) {
            handler.next(error);
          }
        },
      ),
    );
  }

  static bool _isPublicEndpoint(String path) {
    return path.contains('/auth/api/v1/auth/') ||
        path.contains('/actuator/');
  }

  // ================================================================
  // REFRESH ACCESS TOKEN
  // ================================================================

  Future<String?> _refreshAccessToken() async {
    // If a refresh is already in progress, await the ongoing request
    if (_refreshCompleter != null) {
      return _refreshCompleter!.future;
    }

    final completer = Completer<String?>();
    _refreshCompleter = completer;

    try {
      final refreshToken = await SecureStorage.getRefreshToken();

      if (refreshToken == null || refreshToken.isEmpty) {
        completer.complete(null);
        return null;
      }

      final response = await _refreshDio.post(
        ApiEndpoints.refreshToken(),
        data: {'refreshToken': refreshToken},
        options: Options(headers: {'Content-Type': 'application/json'}),
      );

      final data = response.data;

      final newAccessToken = data['accessToken'];
      final newRefreshToken = data['refreshToken'];

      if (newAccessToken == null) {
        completer.complete(null);
        return null;
      }

      // Save new access token
      await SecureStorage.saveAccessToken(newAccessToken.toString());

      // Backend may rotate refresh token
      if (newRefreshToken != null) {
        await SecureStorage.saveRefreshToken(newRefreshToken.toString());
      }

      if (data['tokenType'] != null) {
        await SecureStorage.saveTokenType(data['tokenType'].toString());
      }

      if (data['expiresIn'] != null) {
        await SecureStorage.saveExpiresIn(
          int.parse(data['expiresIn'].toString()),
        );
      }

      final tokenStr = newAccessToken.toString();
      completer.complete(tokenStr);
      return tokenStr;
    } catch (e) {
      print('Refresh token failed: $e');
      completer.complete(null);
      return null;
    } finally {
      _refreshCompleter = null;
    }
  }

  Dio get dio => _dio;
}
