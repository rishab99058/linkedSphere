import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/core/validators.dart';
import 'package:mobile/features/auth/model/forgot_password_request.dart';
import 'package:mobile/features/auth/repository/authRepository.dart';
import 'package:mobile/features/auth/screen/reset_password.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/api_error_handler.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appTextField.dart';
import 'package:mobile/shared/widgets/appToast.dart';

class ForgotPassword extends StatefulWidget {
  const ForgotPassword({super.key});

  @override
  State<ForgotPassword> createState() => _ForgotPasswordState();
}

class _ForgotPasswordState extends State<ForgotPassword> {
  late final AuthRepository authRepository;
  final ApiClient apiClient = ApiClient();
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _emailController = TextEditingController();

  @override
  void initState() {
    super.initState();
    authRepository = AuthRepository(apiClient);
  }

  @override
  void dispose() {
    _emailController.dispose();
    super.dispose();
  }

  Future<void> _handleForgotPassword() async {
    FocusScope.of(context).unfocus();
    if (_formKey.currentState!.validate()) {
      final request = ForgotPasswordRequest(
        email: _emailController.text.trim(),
      );
      try {
        final response = await authRepository.forgotPassword(request);
        if (!mounted) return;
        AppToast.success(response);
        Navigator.of(context).push(
          MaterialPageRoute(
            builder: (context) =>
                OtpVerificationScreen(email: _emailController.text.trim()),
          ),
        );
      } catch (e) {
        if (!mounted) return;
        AppToast.error(ApiErrorHandler.getMessage(e));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 32),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Image.network(
                  AppConstants.forgotPasswordImage,
                  height: 250,
                  width: 250,
                ),
                SizedBox(height: 10),
                Text(
                  AppConstants.forgotPasswordTitle,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.w800,
                  ),
                ),
                const SizedBox(height: 8),
                Text(
                  AppConstants.forgotPasswordSubtitle,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w500,
                    color: AppColors.textSecondary,
                  ),
                ),
                const SizedBox(height: 32),
                AppTextField(
                  controller: _emailController,
                  hintText: 'Enter your email',
                  labelText: 'Email',
                  keyboardType: TextInputType.emailAddress,
                  prefixIcon: const Icon(
                    Icons.email_outlined,
                    color: AppColors.textPrimary,
                  ),
                  validator: Validators.email,
                ),
                const SizedBox(height: 32),
                SizedBox(
                  width: double.infinity,
                  child: AppButton(
                    onPressed: _handleForgotPassword,
                    text: 'Send Verification Code',
                    color: AppColors.primary,
                    textColor: Colors.white,
                    borderColor: AppColors.primary,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
