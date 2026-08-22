import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/core/validators.dart';
import 'package:mobile/features/auth/screen/login.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appTextField.dart';
import 'package:mobile/shared/widgets/textButton.dart';

class SignUpScreen extends StatefulWidget {
  const SignUpScreen({super.key});

  @override
  State<SignUpScreen> createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController phoneNumberController = TextEditingController();
  final TextEditingController confirmPasswordController =
      TextEditingController();
  bool isObsecure = true;
  bool isObsecureConfirm = true;

  final _formKey = GlobalKey<FormState>();

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    phoneNumberController.dispose();
    confirmPasswordController.dispose();
    super.dispose();
  }

  void _handleSignUp() {
    FocusScope.of(context).unfocus();

    if (_formKey.currentState!.validate()) {
      // Backend login API will come here.
    }
  }

  void _moveToLogin() {
    Navigator.of(
      context,
    ).push(MaterialPageRoute(builder: (context) => LoginScreen()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Container(
            padding: EdgeInsets.symmetric(horizontal: 24, vertical: 32),
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Image.network(AppConstants.icon, height: 90, width: 90),
                  const SizedBox(height: 16),
                  Text(
                    AppConstants.signUpTitle,
                    style: const TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.w800,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    AppConstants.signUpSubtitle,
                    style: const TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.w500,
                      color: AppColors.textSecondary,
                    ),
                  ),

                  const SizedBox(height: 24),

                  // email
                  AppTextField(
                    controller: emailController,
                    hintText: 'Enter your email',
                    labelText: 'Email',
                    keyboardType: TextInputType.emailAddress,
                    prefixIcon: const Icon(
                      Icons.email,
                      color: AppColors.textPrimary,
                    ),
                    validator: Validators.email,
                  ),

                  const SizedBox(height: 16),

                  AppTextField(
                    controller: phoneNumberController,
                    hintText: "Enter your contact no",
                    labelText: "Phone no",
                    keyboardType: TextInputType.phone,
                    prefixIcon: const Icon(
                      Icons.call,
                      color: AppColors.textPrimary,
                    ),
                    validator: Validators.phone,
                  ),

                  const SizedBox(height: 16),

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

                  const SizedBox(height: 32),

                  AppButton(
                    onPressed: _handleSignUp,
                    text: 'Signup',
                    color: AppColors.primary,
                    textColor: Colors.white,
                    borderColor: AppColors.primary,
                  ),

                  const SizedBox(height: 24),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        "Already have an account? ",
                        style: TextStyle(
                          color: AppColors.textPrimary,
                          fontSize: 15,
                          fontWeight: FontWeight.w400,
                        ),
                      ),
                      AppTextButton(text: "Login", onPressed: _moveToLogin),
                    ],
                  ),
                  const SizedBox(height: 12),

                  const Row(
                    children: [
                      Expanded(child: Divider()),
                      Padding(
                        padding: EdgeInsets.symmetric(horizontal: 8),
                        child: Text(
                          'or',
                          style: TextStyle(
                            color: AppColors.textPrimary,
                            fontSize: 15,
                            fontWeight: FontWeight.w400,
                          ),
                        ),
                      ),
                      Expanded(child: Divider()),
                    ],
                  ),
                  const SizedBox(height: 24),

                  AppButton(
                    onPressed: () {},
                    text: 'Continue with Google',
                    color: const Color(0xFFF1F5F9),
                    textColor: Colors.black,
                    borderColor: AppColors.textPrimary,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
