class SignInRespose {
  final String id;
  final String email;
  final String message;

  const SignInRespose({
    required this.id,
    required this.email,
    required this.message,
  });

  factory SignInRespose.fromJson(Map<String, dynamic> json) {
    return SignInRespose(
      id: json['id'] as String,
      email: json['email'] as String,
      message: json['message'] as String,
    );
  }
}
