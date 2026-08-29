import 'package:dio/dio.dart';
import 'package:mobile/config/appConfig.dart';

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
}
