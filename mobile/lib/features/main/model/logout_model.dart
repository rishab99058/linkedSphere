class LogoutModel {
  String refreshToken;

  LogoutModel({required this.refreshToken});

  factory LogoutModel.fromJson(Map<String, dynamic> json) {
    return LogoutModel(refreshToken: json['refreshToken']);
  }

  Map<String, dynamic> toJson() {
    return {'refreshToken': refreshToken};
  }
}
