import 'package:dio/dio.dart' as dio;
import 'package:mobile/config/appConfig.dart';
import 'package:mobile/core/navigation_service.dart';
import 'package:mobile/features/auth/model/login_response.dart';
import 'package:mobile/network/apiEndpoints.dart';
import 'package:mobile/storage/secure_storage.dart';

class AuthRefreshClient {
  final dio.Dio _dio;

  AuthRefreshClient()
    : _dio = dio.Dio(
        dio.BaseOptions(
          baseUrl: AppConfig.baseUrl,
          connectTimeout: const Duration(seconds: 10),
          receiveTimeout: const Duration(seconds: 10),
          sendTimeout: const Duration(seconds: 10),
        ),
      );

  Future<String> refreshAccessToken() async {
    final refreshToken = await SecureStorage.getRefreshToken();

    if (refreshToken == null || refreshToken.isEmpty) {
      await forceAutoLogout();
      throw Exception('Refresh token not found');
    }

    try {
      final response = await _dio.post(
        ApiEndpoints.refreshToken(),
        data: {'refreshToken': refreshToken},
      );

      final loginResponse = LoginResponse.fromJson(response.data);

      await SecureStorage.saveAccessToken(loginResponse.accessToken);
      await SecureStorage.saveRefreshToken(loginResponse.refreshToken);
      await SecureStorage.saveTokenType(loginResponse.tokenType);
      await SecureStorage.saveExpiresIn(loginResponse.expiresIn);

      return loginResponse.accessToken;
    } on dio.DioException catch (e) {
      final statusCode = e.response?.statusCode;
      if (statusCode == 401 || statusCode == 403 || statusCode == 400) {
        await forceAutoLogout(message: 'Session expired. Please log in again.');
      }
      rethrow;
    } catch (e) {
      rethrow;
    }
  }
}
