import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';

enum AppDialogType { success, error, warning, info }

class AppDialog {
  AppDialog._();

  // ------------------------------------------------------------
  // SUCCESS
  // ------------------------------------------------------------

  static Future<bool?> success({
    required BuildContext context,
    required String title,
    required String message,
    String confirmText = 'OK',
    String? cancelText,
    bool barrierDismissible = true,
  }) {
    return _show(
      context: context,
      type: AppDialogType.success,
      title: title,
      message: message,
      confirmText: confirmText,
      cancelText: cancelText,
      barrierDismissible: barrierDismissible,
    );
  }

  // ------------------------------------------------------------
  // ERROR
  // ------------------------------------------------------------

  static Future<bool?> error({
    required BuildContext context,
    required String title,
    required String message,
    String confirmText = 'OK',
    String? cancelText,
    bool barrierDismissible = true,
  }) {
    return _show(
      context: context,
      type: AppDialogType.error,
      title: title,
      message: message,
      confirmText: confirmText,
      cancelText: cancelText,
      barrierDismissible: barrierDismissible,
    );
  }

  // ------------------------------------------------------------
  // WARNING
  // ------------------------------------------------------------

  static Future<bool?> warning({
    required BuildContext context,
    required String title,
    required String message,
    String confirmText = 'OK',
    String? cancelText,
    bool barrierDismissible = true,
  }) {
    return _show(
      context: context,
      type: AppDialogType.warning,
      title: title,
      message: message,
      confirmText: confirmText,
      cancelText: cancelText,
      barrierDismissible: barrierDismissible,
    );
  }

  // ------------------------------------------------------------
  // INFO
  // ------------------------------------------------------------

  static Future<bool?> info({
    required BuildContext context,
    required String title,
    required String message,
    String confirmText = 'OK',
    String? cancelText,
    bool barrierDismissible = true,
  }) {
    return _show(
      context: context,
      type: AppDialogType.info,
      title: title,
      message: message,
      confirmText: confirmText,
      cancelText: cancelText,
      barrierDismissible: barrierDismissible,
    );
  }

  // ------------------------------------------------------------
  // CONFIRM (Convenience Method)
  // ------------------------------------------------------------

  static Future<bool?> confirm({
    required BuildContext context,
    AppDialogType type = AppDialogType.warning,
    required String title,
    required String message,
    String confirmText = 'Confirm',
    String cancelText = 'Cancel',
    bool barrierDismissible = true,
  }) {
    return _show(
      context: context,
      type: type,
      title: title,
      message: message,
      confirmText: confirmText,
      cancelText: cancelText,
      barrierDismissible: barrierDismissible,
    );
  }

  // ------------------------------------------------------------
  // COMMON DIALOG
  // ------------------------------------------------------------

  static Future<bool?> _show({
    required BuildContext context,
    required AppDialogType type,
    required String title,
    required String message,
    required String confirmText,
    String? cancelText,
    required bool barrierDismissible,
  }) {
    return showDialog<bool>(
      context: context,
      barrierDismissible: barrierDismissible,
      builder: (context) {
        final config = _getConfig(type);

        return AlertDialog(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(18),
          ),

          // ------------------------------------------------------
          // ICON
          // ------------------------------------------------------
          icon: Container(
            height: 56,
            width: 56,
            decoration: BoxDecoration(
              color: config.color.withValues(alpha: 0.12),
              shape: BoxShape.circle,
            ),
            child: Icon(config.icon, color: config.color, size: 30),
          ),

          // ------------------------------------------------------
          // TITLE
          // ------------------------------------------------------
          title: Text(
            title,
            textAlign: TextAlign.center,
            style: const TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.w700,
              color: AppColors.textPrimary,
            ),
          ),

          // ------------------------------------------------------
          // MESSAGE
          // ------------------------------------------------------
          content: Text(
            message,
            textAlign: TextAlign.center,
            style: const TextStyle(
              fontSize: 15,
              height: 1.4,
              color: AppColors.textSecondary,
            ),
          ),

          // ------------------------------------------------------
          // ACTIONS
          // ------------------------------------------------------
          actionsAlignment: MainAxisAlignment.center,

          actions: [
            if (cancelText != null)
              Row(
                children: [
                  Expanded(
                    child: OutlinedButton(
                      onPressed: () {
                        Navigator.of(context).pop(false);
                      },
                      style: OutlinedButton.styleFrom(
                        foregroundColor: AppColors.textSecondary,
                        side: BorderSide(color: Colors.grey.shade300),
                        padding: const EdgeInsets.symmetric(vertical: 13),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                      ),
                      child: Text(
                        cancelText,
                        style: const TextStyle(fontWeight: FontWeight.w600),
                      ),
                    ),
                  ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: ElevatedButton(
                      onPressed: () {
                        Navigator.of(context).pop(true);
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: config.color,
                        foregroundColor: Colors.white,
                        elevation: 0,
                        padding: const EdgeInsets.symmetric(vertical: 13),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                      ),
                      child: Text(
                        confirmText,
                        style: const TextStyle(fontWeight: FontWeight.w600),
                      ),
                    ),
                  ),
                ],
              )
            else
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pop(true);
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: config.color,
                    foregroundColor: Colors.white,
                    elevation: 0,
                    padding: const EdgeInsets.symmetric(vertical: 13),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  child: Text(
                    confirmText,
                    style: const TextStyle(fontWeight: FontWeight.w600),
                  ),
                ),
              ),
          ],
        );
      },
    );
  }

  // ------------------------------------------------------------
  // CONFIG
  // ------------------------------------------------------------

  static _DialogConfig _getConfig(AppDialogType type) {
    switch (type) {
      case AppDialogType.success:
        return const _DialogConfig(
          icon: Icons.check_circle_outline,
          color: Colors.green,
        );

      case AppDialogType.error:
        return const _DialogConfig(
          icon: Icons.error_outline,
          color: Colors.red,
        );

      case AppDialogType.warning:
        return const _DialogConfig(
          icon: Icons.warning_amber_outlined,
          color: Colors.orange,
        );

      case AppDialogType.info:
        return const _DialogConfig(
          icon: Icons.info_outline,
          color: Colors.blue,
        );
    }
  }
}

class _DialogConfig {
  final IconData icon;
  final Color color;

  const _DialogConfig({required this.icon, required this.color});
}
