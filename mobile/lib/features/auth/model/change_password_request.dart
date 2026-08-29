class ChangePasswordRequest {
  final String email;
  final String otp;
  final String password;

  ChangePasswordRequest({
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
