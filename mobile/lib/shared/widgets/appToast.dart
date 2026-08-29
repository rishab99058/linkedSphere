import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/material.dart';

class AppToast {
  AppToast._();

  static void success(String message) {
    _show(message, backgroundColor: Colors.green, icon: Icons.check_circle);
  }

  static void error(String message) {
    _show(message, backgroundColor: Colors.red, icon: Icons.error);
  }

  static void warning(String message) {
    _show(message, backgroundColor: Colors.orange, icon: Icons.warning);
  }

  static void info(String message) {
    _show(message, backgroundColor: Colors.blue, icon: Icons.info);
  }

  static void _show(
    String message, {
    required Color backgroundColor,
    required IconData icon,
  }) {
    BotToast.showCustomText(
      toastBuilder: (_) {
        return Container(
          margin: const EdgeInsets.symmetric(horizontal: 20),
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
          decoration: BoxDecoration(
            color: backgroundColor,
            borderRadius: BorderRadius.circular(12),
          ),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Icon(icon, color: Colors.white),
              const SizedBox(width: 10),
              Flexible(
                child: Text(
                  message,
                  style: const TextStyle(
                    color: Colors.white,
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
            ],
          ),
        );
      },
      duration: const Duration(seconds: 3),
    );
  }
}
