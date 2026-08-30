class GoogleSignInRequest {
  final String idToken;

  GoogleSignInRequest({required this.idToken});

  Map<String, dynamic> toJson() {
    return {'idToken': idToken};
  }
}
