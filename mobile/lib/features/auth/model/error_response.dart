class ErrorRespose {
  final bool success;
  final int status;
  final String errors;
  final String message;
  final String errorCode;
  final String timestamp;

  ErrorRespose({
    required this.success,
    required this.status,
    required this.errors,
    required this.message,
    required this.errorCode,
    required this.timestamp,
  });

  factory ErrorRespose.fromJson(Map<String, dynamic> json) {
    return ErrorRespose(
      success: json['success'] as bool,
      status: json['status'] as int,
      errors: json['errors'] as String,
      message: json['message'] as String,
      errorCode: json['errorCode'] as String,
      timestamp: json['timestamp'] as String,
    );
  }
}
