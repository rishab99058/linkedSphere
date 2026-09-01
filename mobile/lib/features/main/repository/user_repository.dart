import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/features/main/model/file_uplaod_response.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/apiEndpoints.dart';

class UserRepository {
  final ApiClient apiClient;

  UserRepository(this.apiClient);

  Future<dynamic> getMyProfile() async {
    try {
      final response = await apiClient.dio.get(ApiEndpoints.getMyProfile());
      return response.data;
    } on DioException catch (e) {
      if (e.response?.statusCode == 404) {
        return null;
      }
      rethrow;
    }
  }

  Future<List<FileUploadResponse>> uploadFiles(List<File> files) async {
    try {
      final formData = FormData();

      for (final file in files) {
        formData.files.add(
          MapEntry('files', await MultipartFile.fromFile(file.path)),
        );
      }

      final response = await apiClient.dio.post(
        ApiEndpoints.uploadFiles(),
        data: formData,
      );

      final List<dynamic> data = response.data;

      return data.map((json) => FileUploadResponse.fromJson(json)).toList();
    } on DioException catch (e) {
      debugPrint(e.response?.data.toString());
      rethrow;
    }
  }
}
