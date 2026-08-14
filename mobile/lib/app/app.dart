import 'package:flutter/material.dart';
import 'package:mobile/app/theme.dart';
import 'package:mobile/features/splash/splash_screen.dart';

class LinkedSphereApp extends StatelessWidget {
  const LinkedSphereApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'LinkedSphere',
      theme: AppTheme.lightTheme,
      home: const SplashScreen(),
    );
  }
}
