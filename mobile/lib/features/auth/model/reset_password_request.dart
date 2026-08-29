class ResetPasswordRequest {
  final String email;
  final String otp;
  final String password;

  ResetPasswordRequest({
    required this.email,
    required this.otp,
    required this.password,
  });

  Map<String, dynamic> toJson() => {
        'email': email,
        'otp': otp,
        'password': password,
      };
}
