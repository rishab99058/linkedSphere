class ValidationMessages {
  ValidationMessages._();

  static const String emailRequired = 'Email is required';
  static const String invalidEmail = 'Enter a valid email';

  static const String passwordRequired = 'Password is required';
  static const String passwordMinLength =
      'Password must be at least 8 characters';

  static const String phoneRequired = 'Phone number is required';
  static const String invalidPhone = 'Enter a valid 10-digit phone number';

  static const String nameRequired = 'Name is required';
  static const String passwordMismatch = 'Passwords do not match';

  static const String confirmPasswordRequired = 'Please confirm your password';
}
