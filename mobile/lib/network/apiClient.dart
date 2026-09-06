import 'package:dio/dio.dart';
import 'package:mobile/config/appConfig.dart';
import 'package:mobile/network/authTokenClient.dart';
import 'package:mobile/network/requestType.dart';
import 'package:mobile/storage/secure_storage.dart';

class ApiClient {
  final Dio _dio;
  ApiClient()
    : _dio = Dio(
        BaseOptions(
          baseUrl: AppConfig.baseUrl,
          connectTimeout: const Duration(seconds: 10),
          receiveTimeout: const Duration(seconds: 10),
          sendTimeout: const Duration(seconds: 10),
          headers: {'Accept': 'application/json'},
        ),
      );

  Dio get dio => _dio;

  Future<Response<T>> request<T>({
    required HttpMethod method,
    required String path,
    bool requiresAuth = false,
    ContentType requestType = ContentType.json,
    ApiResponseType responseType = ApiResponseType.json,
    Map<String, String>? headers,
    dynamic data,
  }) async {
    String? accessToken;

    if (requiresAuth) {
      accessToken = await SecureStorage.getAccessToken();
      if (accessToken == null || accessToken.isEmpty) {
        final authRefreshClient = AuthRefreshClient();
        accessToken = await authRefreshClient.refreshAccessToken();
      }
    }

    final requestHeaders = <String, String>{
      'Accept': 'application/json',
      ...?headers,
      if (accessToken != null) 'Authorization': 'Bearer $accessToken',
    };

    final options = Options(
      headers: requestHeaders,
      contentType: requestType == ContentType.multipart
          ? Headers.multipartFormDataContentType
          : Headers.jsonContentType,
      responseType: switch (responseType) {
        ApiResponseType.json => ResponseType.json,
        ApiResponseType.blob => ResponseType.bytes,
        ApiResponseType.text => ResponseType.plain,
      },
    );

    try {
      return await _sendRequest<T>(method, path, data, options);
    } on DioException catch (e) {
      if (requiresAuth && e.response?.statusCode == 401) {
        final authRefreshClient = AuthRefreshClient();
        final newAccessToken = await authRefreshClient.refreshAccessToken();
        final retryHeaders = <String, String>{
          'Accept': 'application/json',
          ...?headers,
          'Authorization': 'Bearer $newAccessToken',
        };
        final retryOptions = options.copyWith(headers: retryHeaders);
        return await _sendRequest<T>(method, path, data, retryOptions);
      }
      rethrow;
    }
  }

  Future<Response<T>> _sendRequest<T>(
    HttpMethod method,
    String path,
    dynamic data,
    Options options,
  ) async {
    return switch (method) {
      HttpMethod.get => await _dio.get<T>(path, options: options),
      HttpMethod.post => await _dio.post<T>(path, data: data, options: options),
      HttpMethod.put => await _dio.put<T>(path, data: data, options: options),
      HttpMethod.patch => await _dio.patch<T>(
        path,
        data: data,
        options: options,
      ),
      HttpMethod.delete => await _dio.delete<T>(
        path,
        data: data,
        options: options,
      ),
    };
  }
}

