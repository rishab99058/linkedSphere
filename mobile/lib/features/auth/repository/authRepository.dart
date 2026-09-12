import 'package:mobile/features/auth/model/change_password_request.dart';
import 'package:mobile/features/auth/model/change_password_respose.dart';
import 'package:mobile/features/auth/model/forgot_password_request.dart';
import 'package:mobile/features/auth/model/google_sign_in_request.dart';
import 'package:mobile/features/auth/model/login_request.dart';
import 'package:mobile/features/auth/model/login_response.dart';
import 'package:mobile/features/auth/model/signin_request.dart';
import 'package:mobile/features/auth/model/signin_response.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/apiEndpoints.dart';
import 'package:mobile/storage/secure_storage.dart';

class AuthRepository {
  final ApiClient apiClient;

  AuthRepository(this.apiClient);

  Future<LoginResponse> login(LoginRequest request) async {
    final response = await apiClient.dio.post(
      ApiEndpoints.login(),
      data: request.toJson(),
    );

    return LoginResponse.fromJson(response.data);
  }

  Future<SignInRespose> signin(SigninRequest request) async {
    final response = await apiClient.dio.post(
      ApiEndpoints.register(),
      data: request.toJson(),
    );

    return SignInRespose.fromJson(response.data);
  }

  Future<String> forgotPassword(ForgotPasswordRequest request) async {
    await apiClient.dio.post(
      ApiEndpoints.forgotPassword(),
      data: request.toJson(),
    );
    return 'Otp sent successfully on ${request.email}';
  }

  Future<ChangePasswordResponse> resetPassword(
    ChangePasswordRequest request,
  ) async {
    final response = await apiClient.dio.post(
      ApiEndpoints.resetPassword(),
      data: request.toJson(),
    );
    return ChangePasswordResponse.fromJson(response.data);
  }

  Future<LoginResponse> googleLogin(GoogleSignInRequest request) async {
    final response = await apiClient.dio.post(
      ApiEndpoints.googleLogin(),
      data: request.toJson(),
    );
    return LoginResponse.fromJson(response.data);
  }

  Future<LoginResponse> refreshAccessToken() async {
    final refreshToken = await SecureStorage.getRefreshToken();

    final response = await apiClient.dio.post(
      ApiEndpoints.refreshToken(),
      data: {'refreshToken': refreshToken},
    );
    final loginResponse = LoginResponse.fromJson(response.data);
    await SecureStorage.saveAccessToken(loginResponse.accessToken);
    await SecureStorage.saveRefreshToken(loginResponse.refreshToken);
    await SecureStorage.saveTokenType(loginResponse.tokenType);
    await SecureStorage.saveExpiresIn(loginResponse.expiresIn);

    return loginResponse;
  }
}
