import 'dart:ui';

import 'package:dio/dio.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_crashlytics/firebase_crashlytics.dart';
import 'package:flutter/material.dart';
import 'package:mobile/app/app.dart';
import 'package:mobile/config/appConfig.dart';
import 'package:mobile/core/google_service_auth.dart';
import 'package:mobile/network/apiClient.dart';
import 'firebase_options.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  FlutterError.onError = FirebaseCrashlytics.instance.recordFlutterFatalError;
  PlatformDispatcher.instance.onError = (error, stack) {
    FirebaseCrashlytics.instance.recordError(error, stack, fatal: true);

    return true;
  };
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
