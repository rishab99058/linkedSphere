import 'dart:io';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';

/// A reusable profile header widget that displays a cover image and an avatar.
/// Supports both network URLs (coverImageUrl, profileImageUrl) and local Files (coverImageFile, profileImageFile).
/// Both images are optional.
class ProfileHeaderImages extends StatelessWidget {
  final String? coverImageUrl;
  final String? profileImageUrl;
  final File? coverImageFile;
  final File? profileImageFile;
  final VoidCallback? onCoverTap;
  final VoidCallback? onProfileTap;
  final double coverHeight;
  final double avatarRadius;
  final double avatarLeftPosition;
  final EdgeInsetsGeometry padding;
  final Widget? coverPlaceholder;
  final Widget? avatarPlaceholder;

  const ProfileHeaderImages({
    super.key,
    this.coverImageUrl,
    this.profileImageUrl,
    this.coverImageFile,
    this.profileImageFile,
    this.onCoverTap,
    this.onProfileTap,
    this.coverHeight = 210,
    this.avatarRadius = 56,
    this.avatarLeftPosition = 20,
    this.padding = const EdgeInsets.symmetric(horizontal: 16),
    this.coverPlaceholder,
    this.avatarPlaceholder,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final scaffoldBg = theme.scaffoldBackgroundColor;
    final primaryColor = theme.colorScheme.primary;

    return Padding(
      padding: padding,
      child: Column(
        children: [
          Stack(
            clipBehavior: Clip.none,
            children: [
              // -------------------------------------------------
              // COVER IMAGE
              // -------------------------------------------------
              GestureDetector(
                onTap: onCoverTap,
                child: Container(
                  height: coverHeight,
                  width: double.infinity,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    color: Colors.grey.shade200,
                  ),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: _buildCoverContent(),
                  ),
                ),
              ),

              // -------------------------------------------------
              // AVATAR IMAGE
              // -------------------------------------------------
              Positioned(
                left: avatarLeftPosition,
                bottom: -avatarRadius,
                child: GestureDetector(
                  onTap: onProfileTap,
                  child: Container(
                    padding: const EdgeInsets.all(4),
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: scaffoldBg,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withValues(alpha: 0.08),
                          blurRadius: 10,
                          offset: const Offset(0, 4),
                        ),
                      ],
                    ),
                    child: CircleAvatar(
                      radius: avatarRadius,
                      backgroundColor: Colors.grey.shade200,
                      child: ClipOval(
                        child: SizedBox(
                          width: avatarRadius * 2,
                          height: avatarRadius * 2,
                          child: _buildAvatarContent(primaryColor),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),

          // Space for protruding avatar
          SizedBox(height: avatarRadius + 14),
        ],
      ),
    );
  }

  Widget _buildCoverContent() {
    // 1. Priority: Local File
    if (coverImageFile != null) {
      return Image.file(
        coverImageFile!,
        fit: BoxFit.cover,
        width: double.infinity,
        height: coverHeight,
      );
    }

    // 2. Network URL
    if (coverImageUrl != null && coverImageUrl!.trim().isNotEmpty) {
      return CachedNetworkImage(
        imageUrl: coverImageUrl!,
        fit: BoxFit.cover,
        placeholder: (context, url) => Container(
          color: Colors.grey.shade200,
          child: const Center(
            child: SizedBox(
              width: 24,
              height: 24,
              child: CircularProgressIndicator(strokeWidth: 2),
            ),
          ),
        ),
        errorWidget: (context, url, error) => _buildDefaultCoverPlaceholder(),
      );
    }

    // 3. Fallback placeholder
    return coverPlaceholder ?? _buildDefaultCoverPlaceholder();
  }

  Widget _buildDefaultCoverPlaceholder() {
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [
            const Color(0xFF1677FF).withValues(alpha: 0.25),
            const Color(0xFF69B1FF).withValues(alpha: 0.15),
          ],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
      ),
      child: Center(
        child: Icon(
          Icons.image_outlined,
          size: 48,
          color: const Color(0xFF1677FF).withValues(alpha: 0.40),
        ),
      ),
    );
  }

  Widget _buildAvatarContent(Color primaryColor) {
    // 1. Priority: Local File
    if (profileImageFile != null) {
      return Image.file(
        profileImageFile!,
        fit: BoxFit.cover,
        width: avatarRadius * 2,
        height: avatarRadius * 2,
      );
    }

    // 2. Network URL
    if (profileImageUrl != null && profileImageUrl!.trim().isNotEmpty) {
      return CachedNetworkImage(
        imageUrl: profileImageUrl!,
        fit: BoxFit.cover,
        placeholder: (context, url) => Container(
          color: Colors.grey.shade200,
          child: const Center(
            child: SizedBox(
              width: 20,
              height: 20,
              child: CircularProgressIndicator(strokeWidth: 2),
            ),
          ),
        ),
        errorWidget: (context, url, error) =>
            _buildDefaultAvatarPlaceholder(primaryColor),
      );
    }

    // 3. Fallback placeholder
    return avatarPlaceholder ?? _buildDefaultAvatarPlaceholder(primaryColor);
  }

  Widget _buildDefaultAvatarPlaceholder(Color primaryColor) {
    return Container(
      color: primaryColor.withValues(alpha: 0.12),
      child: Center(
        child: Icon(
          Icons.person_outline_rounded,
          size: avatarRadius * 0.95,
          color: primaryColor,
        ),
      ),
    );
  }
}
