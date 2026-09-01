import 'dart:async'; // 1. Imported for Timer
import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/features/auth/model/forgot_password_request.dart';
import 'package:mobile/features/auth/repository/authRepository.dart';
import 'package:mobile/features/auth/screen/change_password.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/network/api_error_handler.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appToast.dart';
import 'package:mobile/shared/widgets/textButton.dart';
import 'package:pin_code_fields/pin_code_fields.dart';

class OtpVerificationScreen extends StatefulWidget {
  final String email;
  const OtpVerificationScreen({super.key, required this.email});

  @override
  State<OtpVerificationScreen> createState() => _OtpVerificationScreenState();
}

class _OtpVerificationScreenState extends State<OtpVerificationScreen> {
  final _formKey = GlobalKey<FormState>();
  final PinInputController _otpController = PinInputController();
  final ApiClient _apiClient = ApiClient();
  late final AuthRepository _authRepository;

  String _otp = '';
  bool _isResendButtonDisabled = false;
  int _resendTimer = 60;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _authRepository = AuthRepository(_apiClient);
    _startTimer();
  }

  @override
  void dispose() {
    _timer?.cancel();
    _otpController.dispose();
    super.dispose();
  }

  void _startTimer() {
    setState(() {
      _resendTimer = 60;
      _isResendButtonDisabled = true;
    });

    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (_resendTimer == 0) {
        setState(() {
          _timer?.cancel();
          _isResendButtonDisabled = false;
        });
      } else {
        setState(() {
          _resendTimer--;
        });
      }
    });
  }

  void _verifyOtp() {
    FocusScope.of(context).unfocus();
    if (_otp.length != 6) {
      AppToast.error('Please enter a valid 6-digit OTP');
      return;
    }
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) =>
            ChangePasswordScreen(email: widget.email, token: _otp),
      ),
    );
  }

  Future<void> _resendOtp() async {
    if (_isResendButtonDisabled) return;

    final request = ForgotPasswordRequest(email: widget.email);
    try {
      await _authRepository.forgotPassword(request);
      if (!mounted) return;
      AppToast.success('OTP sent successfully');

      _startTimer();
    } catch (e) {
      if (!mounted) return;
      debugPrint('Error: $e');
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
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Image.network(
                  AppConstants.resetPasswordImage,
                  height: 250,
                  width: 250,
                ),
                const SizedBox(height: 20),
                Text(
                  AppConstants.verifyEmailText,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 10),
                Text(
                  '${AppConstants.verifyEmailSubtitle}\n${widget.email}',
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 16,
                    color: AppColors.textSecondary,
                  ),
                ),
                const SizedBox(height: 30),
                MaterialPinField(
                  length: 6,
                  pinController: _otpController,
                  keyboardType: TextInputType.number,
                  theme: MaterialPinTheme(
                    shape: MaterialPinShape.outlined,
                    cellSize: const Size(48, 52),
                    borderRadius: BorderRadius.circular(8),
                    borderColor: AppColors.border,
                    focusedBorderColor: AppColors.primary,
                    fillColor: AppColors.surface,
                  ),
                  onChanged: (value) {
                    setState(() {
                      _otp = value;
                    });
                  },
                  onCompleted: (value) {
                    setState(() {
                      _otp = value;
                    });
                  },
                ),
                const SizedBox(height: 30),
                AppButton(
                  onPressed: _otp.length == 6 ? _verifyOtp : () {},
                  text: 'Verify OTP',
                  color: AppColors.primary,
                  textColor: Colors.white,
                  borderColor: AppColors.primary,
                ),
                const SizedBox(height: 24),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text(
                      "Didn't receive the code? ",
                      style: TextStyle(fontSize: 14),
                    ),
                    // 8. Passing null to onPressed completely disables AppTextButton styles automatically
                    AppTextButton(
                      text: 'Resend OTP',
                      onPressed: _isResendButtonDisabled
                          ? null
                          : () {
                              _resendOtp();
                            },
                    ),
                  ],
                ),
                const SizedBox(height: 16),
                // 9. Conditionally render the remaining timer layout
                Text(
                  _isResendButtonDisabled
                      ? 'Resend available in $_resendTimer seconds'
                      : 'You can resend the OTP now',
                  style: TextStyle(
                    fontSize: 13,
                    color: _isResendButtonDisabled
                        ? AppColors.textSecondary
                        : Colors.green,
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
