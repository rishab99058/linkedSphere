import 'package:flutter/material.dart';
import 'package:mobile/app/theme.dart';
import 'routes.dart';

void main() {
  runApp(const LinkedSphereApp());
}

class LinkedSphereApp extends StatelessWidget {
  const LinkedSphereApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'LinkedSphere',
      home: LinkedSphereApp(),
      theme: AppTheme.lightTheme,
      routerConfig: router,
    );
  }
}

class SplashScreen extends StatelessWidget {
  const SplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text(
          'LinkedSphere',
          style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold),
        ),
      ),
    );
  }
}
