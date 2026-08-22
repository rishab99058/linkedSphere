import 'package:mobile/core/validationMessage.dart';

class Validators {
  Validators._();

  static String? email(String? value) {
    if (value == null || value.trim().isEmpty) {
      return ValidationMessages.emailRequired;
    }

    final email = value.trim();

    if (!email.contains('@')) {
      return ValidationMessages.invalidEmail;
    }

    return null;
  }

  static String? password(String? value) {
    if (value == null || value.isEmpty) {
      return ValidationMessages.passwordRequired;
    }

    if (value.length < 8) {
      return ValidationMessages.passwordMinLength;
    }

    return null;
  }

  static String? phone(String? value) {
    if (value == null || value.trim().isEmpty) {
      return ValidationMessages.phoneRequired;
    }

    final phone = value.trim();

    if (!RegExp(r'^[6-9]\d{9}$').hasMatch(phone)) {
      return ValidationMessages.invalidPhone;
    }

    return null;
  }

  static String? confirmPassword(String? value, String password) {
    if (value == null || value.isEmpty) {
      return ValidationMessages.confirmPasswordRequired;
    }

    if (value != password) {
      return ValidationMessages.passwordMismatch;
    }

    return null;
  }
}
