import 'package:flutter/material.dart';
import 'package:mobile/features/auth/screen/login.dart';
import 'package:mobile/shared/widgets/appToast.dart';
import 'package:mobile/storage/secure_storage.dart';

final GlobalKey<NavigatorState> rootNavigatorKey = GlobalKey<NavigatorState>();

bool _isAutoLoggingOut = false;

Future<void> forceAutoLogout({
  String message = 'Session expired. Please log in again.',
}) async {
  if (_isAutoLoggingOut) return;
  _isAutoLoggingOut = true;

  try {
    await SecureStorage.clearSession();
    AppToast.error(message);

    rootNavigatorKey.currentState?.pushAndRemoveUntil(
      MaterialPageRoute(builder: (context) => const LoginScreen()),
      (route) => false,
    );
  } finally {
    _isAutoLoggingOut = false;
  }
}
