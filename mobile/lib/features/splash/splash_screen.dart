import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/features/onboarding/screens/onboarding.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
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
    Future.delayed(const Duration(seconds: 10), _goToHome);
  }

  void _goToHome() {
    if (!mounted) return;
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(builder: (_) => const OnBoardingScreen()),
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
            width: 350,
            height: 350,
            fit: BoxFit.contain,
            loadingBuilder: (context, child, loadingProgress) {
              if (loadingProgress == null) return child;
              return const SizedBox(
                width: 350,
                height: 350,
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
