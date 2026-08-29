import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/core/google_service_auth.dart';
import 'package:mobile/core/validators.dart';
import 'package:mobile/features/auth/model/login_request.dart';
import 'package:mobile/features/auth/repository/authRepository.dart';
import 'package:mobile/features/auth/screen/forgot_password.dart';
import 'package:mobile/features/auth/screen/signUp.dart';
import 'package:mobile/features/main/bottom_navigation_bar.dart';
import 'package:mobile/features/offline_screen/no_internet.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appTextField.dart';
import 'package:mobile/shared/widgets/appToast.dart';
import 'package:mobile/shared/widgets/textButton.dart';
import 'package:mobile/storage/secure_storage.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final ApiClient _apiClient = ApiClient();
  late final AuthRepository _authRepository;

  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  bool _rememberMe = false;
  bool _isObscure = true;

  @override
  void initState() {
    super.initState();
    _authRepository = AuthRepository(_apiClient);
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    _loadRememberedCredentials();
  }

  Future<void> _handleGoogleSignIn() async {
    try {
      final googleUser = await GoogleAuthService.signIn();

      if (googleUser != null) {
        debugPrint('Google Sign-In Success: ${googleUser.email}');
        AppToast.success('Google Sign-In Successful');
      }
    } catch (e) {
      debugPrint('Google Sign-In Error: $e');
      AppToast.error(e.toString());
    }
  }

  Future<void> _loadRememberedCredentials() async {
    final rememberMe = await SecureStorage.getRememberMe();
    final email = await SecureStorage.getSavedEmail();
    final password = await SecureStorage.getSavedPassword();

    if (mounted && rememberMe && email != null && password != null) {
      setState(() {
        _rememberMe = rememberMe;
        _emailController.text = email;
        _passwordController.text = password;
      });
    }
  }

  Future<void> _saveRememberedCredentials() async {
    if (_rememberMe) {
      await SecureStorage.saveRememberMe(_rememberMe);
      await SecureStorage.saveSavedEmail(_emailController.text);
      await SecureStorage.saveSavedPassword(_passwordController.text);
    } else {
      await SecureStorage.clearRememberedCredentials();
    }
  }

  Future<void> _handleLogin() async {
    FocusScope.of(context).unfocus();

    if (_formKey.currentState!.validate()) {
      // Backend login API will come here.
      final request = LoginRequest(
        email: _emailController.text.trim(),
        password: _passwordController.text,
      );

      try {
        final response = await _authRepository.login(request);

        if (!mounted) return;

        AppToast.success('Login successful');

        debugPrint('Login successful');
        SecureStorage.saveIsLoggedIn(true);
        SecureStorage.saveAccessToken(response.accessToken);
        SecureStorage.saveTokenType(response.tokenType);
        SecureStorage.saveRefreshToken(response.refreshToken);
        SecureStorage.saveExpiresIn(response.expiresIn);
        Navigator.of(
          context,
        ).push(MaterialPageRoute(builder: (context) => const MainScreen()));
      } catch (e) {
        if (!mounted) return;

        debugPrint('Login error: $e');

        AppToast.error(e.toString());
      }
    }
  }

  void _navigateToSignUp() {
    Navigator.of(
      context,
    ).push(MaterialPageRoute(builder: (context) => const SignUpScreen()));
  }

  void _navigateToForgotPasswordScreen() {
    Navigator.of(
      context,
    ).push(MaterialPageRoute(builder: (context) => const ForgotPassword()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 32),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Image.network(AppConstants.icon, height: 90, width: 90),
                const SizedBox(height: 24),
                Text(
                  AppConstants.loginTitle,
                  style: const TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.w800,
                  ),
                ),
                const SizedBox(height: 8),
                Text(
                  AppConstants.loginSubtitle,
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

                const SizedBox(height: 16),

                // ----------------------------------------------------------
                // PASSWORD
                // ----------------------------------------------------------
                AppTextField(
                  controller: _passwordController,
                  hintText: 'Enter your password',
                  labelText: 'Password',
                  obscureText: _isObscure,
                  keyboardType: TextInputType.visiblePassword,
                  prefixIcon: const Icon(
                    Icons.lock_outline,
                    color: AppColors.textPrimary,
                  ),
                  suffixIcon: IconButton(
                    icon: Icon(
                      _isObscure
                          ? Icons.visibility_outlined
                          : Icons.visibility_off_outlined,
                    ),
                    onPressed: () {
                      setState(() {
                        _isObscure = !_isObscure;
                      });
                    },
                  ),
                  validator: Validators.password,
                ),

                const SizedBox(height: 12),

                // ----------------------------------------------------------
                // REMEMBER ME + FORGOT PASSWORD
                // ----------------------------------------------------------
                Row(
                  children: [
                    Checkbox(
                      value: _rememberMe,
                      onChanged: (value) {
                        setState(() {
                          _rememberMe = value ?? false;
                          _saveRememberedCredentials();
                        });
                      },
                      materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                    ),

                    const SizedBox(width: 4),

                    const Text(
                      'Remember me',
                      style: TextStyle(
                        color: AppColors.textPrimary,
                        fontSize: 14,
                      ),
                    ),

                    const Spacer(),

                    AppTextButton(
                      text: 'Forgot Password?',
                      onPressed: () {
                        _navigateToForgotPasswordScreen();
                      },
                    ),
                  ],
                ),

                const SizedBox(height: 24),

                // ----------------------------------------------------------
                // LOGIN BUTTON
                // ----------------------------------------------------------
                SizedBox(
                  width: double.infinity,
                  child: AppButton(
                    onPressed: _handleLogin,
                    text: 'Login',
                    color: AppColors.primary,
                    textColor: Colors.white,
                    borderColor: AppColors.primary,
                  ),
                ),

                const SizedBox(height: 24),

                // ----------------------------------------------------------
                // SIGN UP
                // ----------------------------------------------------------
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text(
                      "Don't have an account? ",
                      style: TextStyle(
                        color: AppColors.textPrimary,
                        fontSize: 14,
                      ),
                    ),
                    AppTextButton(
                      text: 'Sign Up',
                      onPressed: _navigateToSignUp,
                    ),
                  ],
                ),

                const SizedBox(height: 24),

                // ----------------------------------------------------------
                // OR DIVIDER
                // ----------------------------------------------------------
                const Row(
                  children: [
                    Expanded(child: Divider()),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 12),
                      child: Text(
                        'OR',
                        style: TextStyle(
                          color: AppColors.textSecondary,
                          fontSize: 14,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ),
                    Expanded(child: Divider()),
                  ],
                ),

                const SizedBox(height: 24),

                // ----------------------------------------------------------
                // GOOGLE LOGIN
                // ----------------------------------------------------------
                SizedBox(
                  width: double.infinity,
                  child: AppButton(
                    onPressed: _handleGoogleSignIn,
                    text: 'Continue with Google',
                    color: const Color(0xFFF1F5F9),
                    textColor: Colors.black,
                    borderColor: AppColors.textSecondary,
                  ),
                ),

                const SizedBox(height: 16),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
