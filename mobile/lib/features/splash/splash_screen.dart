import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/features/auth/screen/login.dart';
import 'package:mobile/features/onboarding/screens/onboarding.dart';
import 'package:mobile/storage/secure_storage.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  Timer? _timer;

  @override
  void initState() {
    super.initState();

    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.dark,
        statusBarBrightness: Brightness.light,
      ),
    );

    // Navigate to OnBoardingScreen after 3 seconds
    _timer = Timer(const Duration(seconds: 3), _goToHome);
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _goToHome() async {
    if (!mounted) return;
    bool isFirstLaunch = await SecureStorage.getIsFirstLaunch();
    bool isLoggedIn = await SecureStorage.getIsLoggedIn();
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (_) =>
            isFirstLaunch ? const OnBoardingScreen() : const LoginScreen(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(
        0xFFF1F5F9,
      ), // Premium Slate 100 (grayish background)
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(32.0),
          child: Image.network(
            AppConstants.appLogoUrl,
            width: 600,
            height: 600,
            fit: BoxFit.contain,
            loadingBuilder: (context, child, loadingProgress) {
              if (loadingProgress == null) return child;
              return const SizedBox(
                width: 600,
                height: 600,
                child: Center(child: CircularProgressIndicator.adaptive()),
              );
            },
            errorBuilder: (context, error, stackTrace) {
              return const Text(
                'LinkedSphere',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  color: Color(0xFF334155),
                  letterSpacing: 1.2,
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
