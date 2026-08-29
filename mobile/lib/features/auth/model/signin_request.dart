class SigninRequest {
  final String email;
  final String password;
  final String phoneNumber;

  const SigninRequest({
    required this.email,
    required this.password,
    required this.phoneNumber,
  });

  Map<String, dynamic> toJson() => {
    "email": email,
    "password": password,
    "phoneNumber": phoneNumber,
  };
}
