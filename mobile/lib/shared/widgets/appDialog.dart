import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';

export 'package:awesome_dialog/awesome_dialog.dart';

class AppAwesomeDialog {
  AppAwesomeDialog._();

  static AwesomeDialog show({
    required BuildContext context,
    required String title,
    String? desc,
    Widget? body,
    Widget? customHeader,
    IconData? icon,
    Color? iconColor,
    DialogType dialogType = DialogType.info,
    AnimType animType = AnimType.scale,
    String? btnOkText = 'OK',
    VoidCallback? btnOkOnPress,
    Color? btnOkColor,
    String? btnCancelText,
    VoidCallback? btnCancelOnPress,
    Color? btnCancelColor,
    bool dismissOnTouchOutside = true,
    bool dismissOnBackKeyPress = true,
    Function(DismissType type)? onDismissCallback,
  }) {
    final headerWidget =
        customHeader ?? _buildHeader(dialogType, icon, iconColor);

    final dialog = AwesomeDialog(
      context: context,
      dialogType: DialogType.noHeader,
      customHeader: headerWidget,
      animType: animType,
      title: title,
      desc: desc,
      body: body,
      dismissOnTouchOutside: dismissOnTouchOutside,
      dismissOnBackKeyPress: dismissOnBackKeyPress,
      onDismissCallback: onDismissCallback,
      btnOkText: btnOkText,
      btnOkOnPress: btnOkOnPress,
      btnOkColor: btnOkColor ?? _getDefaultButtonColor(dialogType),
      btnCancelText: btnCancelText,
      btnCancelOnPress: btnCancelOnPress,
      btnCancelColor: btnCancelColor ?? AppColors.textMuted,
      titleTextStyle: const TextStyle(
        fontSize: 20,
        fontWeight: FontWeight.w700,
        color: AppColors.textPrimary,
        letterSpacing: -0.3,
      ),
      descTextStyle: const TextStyle(
        fontSize: 14,
        color: AppColors.textSecondary,
        height: 1.4,
      ),
      buttonsTextStyle: const TextStyle(
        fontWeight: FontWeight.w600,
        fontSize: 15,
        color: Colors.white,
      ),
      buttonsBorderRadius: BorderRadius.circular(12),
      dialogBorderRadius: BorderRadius.circular(20),
    );

    dialog.show();
    return dialog;
  }

  static Color _getDefaultButtonColor(DialogType dialogType) {
    switch (dialogType) {
      case DialogType.warning:
        return AppColors.warning;
      case DialogType.error:
        return AppColors.error;
      case DialogType.success:
        return AppColors.success;
      default:
        return AppColors.primary;
    }
  }

  static Widget _buildHeader(
    DialogType dialogType,
    IconData? customIcon,
    Color? customColor,
  ) {
    Color effectiveIconColor;
    IconData iconData;

    switch (dialogType) {
      case DialogType.warning:
        effectiveIconColor = customColor ?? AppColors.warning;
        iconData = customIcon ?? Icons.warning_amber_rounded;
        break;
      case DialogType.error:
        effectiveIconColor = customColor ?? AppColors.error;
        iconData = customIcon ?? Icons.error_outline_rounded;
        break;
      case DialogType.success:
        effectiveIconColor = customColor ?? AppColors.success;
        iconData = customIcon ?? Icons.check_circle_outline_rounded;
        break;
      case DialogType.info:
      case DialogType.infoReverse:
      default:
        effectiveIconColor = customColor ?? AppColors.primary;
        iconData = customIcon ?? Icons.person_outline_rounded;
        break;
    }

    return Container(
      width: 68,
      height: 68,
      decoration: BoxDecoration(
        color: effectiveIconColor.withValues(alpha: 0.12),
        shape: BoxShape.circle,
        border: Border.all(
          color: effectiveIconColor.withValues(alpha: 0.2),
          width: 2,
        ),
      ),
      child: Center(child: Icon(iconData, size: 34, color: effectiveIconColor)),
    );
  }

  static AwesomeDialog showWarning({
    required BuildContext context,
    required String title,
    String? desc,
    String? btnOkText = 'OK',
    VoidCallback? btnOkOnPress,
    String? btnCancelText,
    VoidCallback? btnCancelOnPress,
  }) {
    return show(
      context: context,
      title: title,
      desc: desc,
      dialogType: DialogType.warning,
      animType: AnimType.scale,
      btnOkText: btnOkText,
      btnOkOnPress: btnOkOnPress,
      btnOkColor: AppColors.warning,
      btnCancelText: btnCancelText,
      btnCancelOnPress: btnCancelOnPress,
    );
  }

  static AwesomeDialog showError({
    required BuildContext context,
    required String title,
    String? desc,
    String? btnOkText = 'OK',
    VoidCallback? btnOkOnPress,
  }) {
    return show(
      context: context,
      title: title,
      desc: desc,
      dialogType: DialogType.error,
      animType: AnimType.scale,
      btnOkText: btnOkText,
      btnOkOnPress: btnOkOnPress,
      btnOkColor: AppColors.error,
    );
  }

  static AwesomeDialog showSuccess({
    required BuildContext context,
    required String title,
    String? desc,
    String? btnOkText = 'OK',
    VoidCallback? btnOkOnPress,
  }) {
    return show(
      context: context,
      title: title,
      desc: desc,
      dialogType: DialogType.success,
      animType: AnimType.scale,
      btnOkText: btnOkText,
      btnOkOnPress: btnOkOnPress,
      btnOkColor: AppColors.success,
    );
  }
}
