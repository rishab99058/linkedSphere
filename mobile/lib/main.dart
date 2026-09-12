import 'dart:ui';

import 'package:dio/dio.dart';
import 'package:firebase_analytics/firebase_analytics.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_crashlytics/firebase_crashlytics.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
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
  FirebaseAnalytics.instance;
  debugPrint(AppConfig.baseUrl);
  debugPrint(AppConfig.environment.toString());
  final apiClient = ApiClient();
  await GoogleAuthService.initialize();

  try {
    final response = await apiClient.dio.get('/actuator/health');
    debugPrint('✅ Status Code: ${response.statusCode}');
    debugPrint('✅ Data: ${response.data}');
  } on DioException catch (e) {
    debugPrint('❌ Dio Error Status Code: ${e.response?.statusCode}');
    debugPrint('❌ Dio Error Message: ${e.message}');
    debugPrint('❌ Dio Error Response: ${e.response?.data}');
  } catch (e) {
    debugPrint('❌ General Error: $e');
  }
  runApp(const ProviderScope(child: LinkedSphereApp()));
}
