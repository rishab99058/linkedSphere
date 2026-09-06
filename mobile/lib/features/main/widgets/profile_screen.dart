import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/features/auth/screen/login.dart';
import 'package:mobile/features/main/model/profile_model.dart';
import 'package:mobile/features/main/model/profile_not_foud_exception.dart';
import 'package:mobile/features/main/repository/profile_repository.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appDialog.dart';
import 'package:mobile/shared/widgets/appToast.dart';
import 'package:mobile/storage/secure_storage.dart';

final apiClientProvider = Provider<ApiClient>((ref) {
  return ApiClient();
});

final profileRepositoryProvider = Provider<ProfileRepository>((ref) {
  final apiClient = ref.watch(apiClientProvider);
  return ProfileRepository(apiClient);
});

class ProfileScreen extends ConsumerStatefulWidget {
  const ProfileScreen({super.key});

  @override
  ConsumerState<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends ConsumerState<ProfileScreen> {
  ProfileResponse? _profile;

  bool _isLoading = true;
  String? _errorMessage;

  @override
  void initState() {
    super.initState();

    WidgetsBinding.instance.addPostFrameCallback((_) {
      _fetchProfile();
    });
  }

  Future<void> _fetchProfile() async {
    try {
      final profileRepository = ref.read(profileRepositoryProvider);

      final profile = await profileRepository.getMyProfile();

      if (!mounted) return;

      setState(() {
        _profile = profile;
        _isLoading = false;
        _errorMessage = null;
      });
    } on ProfileNotFoundException {
      if (!mounted) return;

      setState(() {
        _isLoading = false;
        _profile = null;
        _errorMessage = null;
      });
    } catch (e) {
      if (!mounted) return;

      setState(() {
        _isLoading = false;
        _errorMessage = e.toString();
      });
    }
  }

  void _openCreateProfile() {
    // TODO: Navigate to create profile screen
  }

  void _openEditProfile() {
    if (_profile == null) return;
    // TODO: Navigate to edit profile screen
  }

  void _handleLogout() {
    AppAwesomeDialog.show(
      context: context,
      dialogType: DialogType.warning,
      animType: AnimType.scale,
      title: 'Logout',
      desc: 'Are you sure you want to logout?',
      btnCancelText: 'Cancel',
      btnOkText: 'Logout',
      btnCancelOnPress: () {},
      btnOkOnPress: () {
        logout();
      },
      dismissOnTouchOutside: false,
      dismissOnBackKeyPress: false,
    );
  }

  Future<void> logout() async {
    final profileRepository = ref.read(profileRepositoryProvider);
    try {
      await profileRepository.logOut();
    } catch (e) {
      debugPrint('Logout error: $e');
    }

    await SecureStorage.clearSession();
    if (!mounted) return;
    AppToast.success('Logout successful');
    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(builder: (context) => const LoginScreen()),
      (route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        actions: [
          IconButton(
            onPressed: _openEditProfile,
            icon: const Icon(Icons.edit, color: AppColors.primary),
          ),
          IconButton(
            icon: const Icon(Icons.logout, color: AppColors.primary),
            onPressed: _handleLogout,
          ),
        ],
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    if (_isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (_errorMessage != null) {
      return SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Image.network(
                "https://res.cloudinary.com/dws1oujlk/image/upload/v1788703852/ChatGPT_Image_Sep_6_2026_07_40_14_PM_cwhcps.png",
                height: 200,
                width: 200,
                fit: BoxFit.cover,
              ),
            ],
          ),
        ),
      );
    }

    if (_profile == null) {
      return SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Image.network(
                  "https://res.cloudinary.com/dws1oujlk/image/upload/v1788703852/ChatGPT_Image_Sep_6_2026_07_40_14_PM_cwhcps.png",
                  height: 300,
                  width: 300,
                  fit: BoxFit.cover,
                ),
                const SizedBox(height: 16),
                const Text(
                  'Profile Not Set Up',
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 8),
                const Text(
                  'Your profile is not set up yet. Create your profile to highlight your experience, skills, and professional background.',
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 16),
                ),
                const SizedBox(height: 16),
                SizedBox(
                  width: double.infinity,
                  child: AppButton(
                    onPressed: _openCreateProfile,
                    text: 'Create Profile',
                    color: AppColors.primary,
                    textColor: Colors.white,
                    borderColor: AppColors.primary,
                  ),
                ),
                const SizedBox(height: 16),
                SizedBox(
                  width: double.infinity,
                  child: AppButton(
                    onPressed: _handleLogout,
                    text: 'Logout',
                    color: const Color(0xFFF1F5F9),
                    textColor: Colors.black,
                    borderColor: AppColors.textSecondary,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
    }

    return _buildProfile();
  }

  Widget _buildProfile() {
    return Center(
      child: Text(
        _profile!.fullName,
        style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
      ),
    );
  }
}
