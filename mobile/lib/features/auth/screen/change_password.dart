import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/core/validators.dart';
import 'package:mobile/features/auth/model/change_password_request.dart';
import 'package:mobile/features/auth/model/change_password_respose.dart';
import 'package:mobile/features/auth/repository/authRepository.dart';
import 'package:mobile/features/auth/screen/login.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/api_error_handler.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appTextField.dart';
import 'package:mobile/shared/widgets/appToast.dart';

class ChangePasswordScreen extends StatefulWidget {
  final String email;
  final String token;
  const ChangePasswordScreen({
    super.key,
    required this.email,
    required this.token,
  });

  @override
  State<ChangePasswordScreen> createState() => _ChangePasswordScreenState();
}

class _ChangePasswordScreenState extends State<ChangePasswordScreen> {
  late final AuthRepository authRepository;
  final ApiClient apiClient = ApiClient();
  final passwordController = TextEditingController();
  final confirmPasswordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  bool isObsecure = true;
  bool isObsecureConfirm = true;

  @override
  void initState() {
    authRepository = AuthRepository(apiClient);
    super.initState();
  }

  @override
  void dispose() {
    passwordController.dispose();
    confirmPasswordController.dispose();
    super.dispose();
  }

  Future<void> _changePassword() async {
    FocusScope.of(context).unfocus();

    if (!_formKey.currentState!.validate()) {
      return;
    }

    ChangePasswordRequest req = ChangePasswordRequest(
      email: widget.email,
      otp: widget.token,
      password: passwordController.text,
    );

    try {
      var response = await authRepository.resetPassword(req);

      if (response != null) {
        ChangePasswordResponse res = response;
        if (!mounted) return;
        AppToast.success(res.message);
        Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (context) => const LoginScreen()),
          (route) => false,
        );
      }
    } catch (e) {
      debugPrint('Failed to change password: ${e.toString()}');
      if (!mounted) return;
      AppToast.error(ApiErrorHandler.getMessage(e));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 32),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Image.network(
                AppConstants.changePasswordImage,
                height: 250,
                width: 250,
              ),
              const SizedBox(height: 32),
              Text(
                'Change Password',
                style: Theme.of(context).textTheme.headlineMedium,
              ),
              const SizedBox(height: 12),
              Text(
                'Enter your new password',
                style: Theme.of(context).textTheme.bodyMedium,
              ),
              const SizedBox(height: 12),
              Form(
                key: _formKey,
                child: Column(
                  children: [
                    AppTextField(
                      controller: passwordController,
                      hintText: 'Enter your password',
                      labelText: 'Password',
                      obscureText: isObsecure,
                      prefixIcon: const Icon(
                        Icons.lock,
                        color: AppColors.textPrimary,
                      ),
                      suffixIcon: IconButton(
                        icon: Icon(
                          isObsecure ? Icons.visibility : Icons.visibility_off,
                        ),
                        onPressed: () {
                          setState(() {
                            isObsecure = !isObsecure;
                          });
                        },
                      ),
                      validator: Validators.password,
                    ),

                    const SizedBox(height: 16),

                    AppTextField(
                      controller: confirmPasswordController,
                      hintText: 'Re enter your password',
                      labelText: 'Cofirm Password',
                      obscureText: isObsecureConfirm,
                      prefixIcon: const Icon(
                        Icons.lock,
                        color: AppColors.textPrimary,
                      ),
                      suffixIcon: IconButton(
                        icon: Icon(
                          isObsecureConfirm
                              ? Icons.visibility
                              : Icons.visibility_off,
                        ),
                        onPressed: () {
                          setState(() {
                            isObsecureConfirm = !isObsecureConfirm;
                          });
                        },
                      ),
                      validator: (value) => Validators.confirmPassword(
                        value,
                        passwordController.text,
                      ),
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      width: double.infinity,
                      child: AppButton(
                        onPressed: _changePassword,
                        text: 'Reset Password',
                        color: AppColors.primary,
                        textColor: Colors.white,
                        borderColor: AppColors.primary,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
