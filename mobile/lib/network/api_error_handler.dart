import 'package:dio/dio.dart';

class ApiErrorHandler {
  ApiErrorHandler._();

  static String getMessage(dynamic error) {
    if (error is DioException) {
      if (error.response?.data != null) {
        final data = error.response!.data;
        if (data is Map<String, dynamic>) {
          if (data['message'] != null &&
              data['message'].toString().trim().isNotEmpty) {
            return data['message'].toString();
          }
          if (data['errors'] != null &&
              data['errors'].toString().trim().isNotEmpty) {
            return data['errors'].toString();
          }
          if (data['error'] != null &&
              data['error'].toString().trim().isNotEmpty) {
            return data['error'].toString();
          }
        } else if (data is String && data.trim().isNotEmpty) {
          return data;
        }
      }

      switch (error.type) {
        case DioExceptionType.connectionTimeout:
        case DioExceptionType.sendTimeout:
        case DioExceptionType.receiveTimeout:
          return 'Connection timed out. Please check your internet connection.';
        case DioExceptionType.connectionError:
          return 'Unable to reach server. Please check your internet connection.';
        case DioExceptionType.badResponse:
          final statusCode = error.response?.statusCode;
          if (statusCode == 401) {
            return 'Invalid email or password';
          } else if (statusCode == 403) {
            return 'Access denied';
          } else if (statusCode == 404) {
            return 'Requested resource not found';
          } else if (statusCode == 409) {
            return 'Resource already exists';
          } else if (statusCode != null && statusCode >= 500) {
            return 'Server error occurred. Please try again later.';
          }
          return 'Request failed (${statusCode ?? 'unknown'})';
        case DioExceptionType.cancel:
          return 'Request was cancelled';
        default:
          return error.message ?? 'An unexpected error occurred';
      }
    }

    if (error is Exception) {
      return error.toString().replaceFirst('Exception: ', '');
    }

    return error?.toString() ?? 'An unexpected error occurred';
  }
}
