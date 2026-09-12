import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:image_picker/image_picker.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/shared/widgets/profileHeaderImages.dart';
import 'package:mobile/shared/widgets/sectionHeader.dart';

class ProfileFormScreen extends ConsumerStatefulWidget {
  final bool isUpdate;
  final String? initialCoverImageUrl;
  final String? initialProfileImageUrl;

  const ProfileFormScreen({
    super.key,
    this.isUpdate = false,
    this.initialCoverImageUrl,
    this.initialProfileImageUrl,
  });

  @override
  ConsumerState<ProfileFormScreen> createState() => _ProfileFormScreenState();
}

class _ProfileFormScreenState extends ConsumerState<ProfileFormScreen> {
  final ImagePicker _picker = ImagePicker();

  String? _coverImageUrl;
  String? _profileImageUrl;

  File? _coverImageFile;
  File? _profileImageFile;

  @override
  void initState() {
    super.initState();
    _coverImageUrl = widget.initialCoverImageUrl;
    _profileImageUrl = widget.initialProfileImageUrl;
  }

  Future<void> _onPickCoverImage() async {
    try {
      final XFile? image = await _picker.pickImage(
        source: ImageSource.gallery,
        maxWidth: 1920,
        maxHeight: 1080,
        imageQuality: 85,
      );

      if (image == null) return;

      setState(() {
        _coverImageFile = File(image.path);
      });
    } catch (e) {
      debugPrint('Error picking cover image: $e');
    }
  }

  Future<void> _onPickProfileImage() async {
    try {
      final XFile? image = await _picker.pickImage(
        source: ImageSource.gallery,
        maxWidth: 1000,
        maxHeight: 1000,
        imageQuality: 85,
      );

      if (image == null) return;

      setState(() {
        _profileImageFile = File(image.path);
      });
    } catch (e) {
      debugPrint('Error picking profile image: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.isUpdate ? 'Update Profile' : 'Create Profile',
          style: const TextStyle(fontWeight: FontWeight.w600),
        ),
        actions: [
          TextButton(
            onPressed: () {
              // TODO: Save profile with _profileImageFile / _coverImageFile
            },
            child: const Text(
              'Save',
              style: TextStyle(fontWeight: FontWeight.w600),
            ),
          ),
        ],
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            const SizedBox(height: 12),

            ProfileHeaderImages(
              coverImageUrl: _coverImageUrl,
              profileImageUrl: _profileImageUrl,
              coverImageFile: _coverImageFile,
              profileImageFile: _profileImageFile,
              onCoverTap: _onPickCoverImage,
              onProfileTap: _onPickProfileImage,
            ),

            const SizedBox(height: 12),

            // Option to select Profile and Cover Photo
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton.icon(
                  onPressed: _onPickProfileImage,
                  icon: const Icon(Icons.person),
                  label: const Text('Profile Photo'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                ),
                ElevatedButton.icon(
                  onPressed: _onPickCoverImage,
                  icon: const Icon(Icons.image),
                  label: const Text('Cover Photo'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),

            // Basic Information Section Header
            const SectionHeader(
              icon: Icons.file_copy_rounded,
              title: 'Basic Information',
              subtitle: 'Tell us a little about yourself',
            ),

            // Contact Information
            // Professional Information
            // etc.
          ],
        ),
      ),
    );
  }
}
