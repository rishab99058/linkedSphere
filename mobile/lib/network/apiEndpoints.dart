class ApiEndpoints {
  ApiEndpoints._();

  // Gateway route: /auth/** -> auth-service (context-path: /auth, request mapping: /api/v1/auth)
  static const String _authPrefix = '/auth/api/v1/auth';
  static const String _userPrefix = '/users';

  // Auth Endpoints
  static String login() => '$_authPrefix/login';
  static String register() => '$_authPrefix/register';
  static String refreshToken() => '$_authPrefix/refresh';
  static String forgotPassword() => '$_authPrefix/forgot-password';
  static String resetPassword() => '$_authPrefix/reset-password';
  static String googleLogin() => '$_authPrefix/google';
  static String logout() => '$_authPrefix/logout';

  // Health
  static String health() => '/actuator/health';

  // Profile Endpoints
  static String getMyProfile() => '$_userPrefix/api/v1/users/get_my_profile';
}
