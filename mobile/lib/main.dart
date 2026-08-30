import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/app/app.dart';
import 'package:mobile/config/appConfig.dart';
import 'package:mobile/core/google_service_auth.dart';
import 'package:mobile/network/apiClient.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  debugPrint(AppConfig.baseUrl);
  debugPrint(AppConfig.environment.toString());
  final apiClient = ApiClient();
  await GoogleAuthService.initialize();

  try {
    final response = await apiClient.dio.get('/actuator/health');
    print('✅ Status Code: ${response.statusCode}');
    print('✅ Data: ${response.data}');
  } on DioException catch (e) {
    print('❌ Dio Error Status Code: ${e.response?.statusCode}');
    print('❌ Dio Error Message: ${e.message}');
    print('❌ Dio Error Response: ${e.response?.data}');
  } catch (e) {
    print('❌ General Error: $e');
  }
  runApp(const LinkedSphereApp());
}
