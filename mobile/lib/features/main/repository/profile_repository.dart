import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:mobile/features/main/model/logout_model.dart';
import 'package:mobile/features/main/model/profile_model.dart';
import 'package:mobile/features/main/model/profile_not_foud_exception.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/apiEndpoints.dart';
import 'package:mobile/network/requestType.dart';
import 'package:mobile/storage/secure_storage.dart';

class ProfileRepository {
  final ApiClient apiClient;

  ProfileRepository(this.apiClient);

  Future<ProfileResponse> getMyProfile() async {
    try {
      final response = await apiClient.request(
        method: HttpMethod.get,
        path: ApiEndpoints.getMyProfile(),
        requiresAuth: true,
      );

      return ProfileResponse.fromJson(response.data);
    } on DioException catch (e) {
      final data = e.response?.data;

      if (e.response?.statusCode == 404 &&
          data is Map<String, dynamic> &&
          data['errorCode'] == 'PROFILE_NOT_FOUND') {
        throw ProfileNotFoundException(
          data['message'] ?? 'Profile has not been set up',
        );
      }

      rethrow;
    }
  }

  Future<void> logOut() async {
    final refreshToken = await SecureStorage.getRefreshToken();
    if (refreshToken != null && refreshToken.isNotEmpty) {
      try {
        await apiClient.request(
          method: HttpMethod.post,
          path: ApiEndpoints.logout(),
          requiresAuth: false,
          responseType: ApiResponseType.text,
          data: LogoutModel(
            refreshToken: refreshToken,
          ).toJson(),
        );
      } catch (e) {
        // Backend error logged, client will still clear session
        debugPrint('Backend logout API notice: $e');
      }
    }
  }
}
