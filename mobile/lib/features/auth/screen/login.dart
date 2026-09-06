import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mobile/core/colors.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/core/google_service_auth.dart';
import 'package:mobile/core/validators.dart';
import 'package:mobile/features/auth/model/google_sign_in_request.dart';
import 'package:mobile/features/auth/model/login_request.dart';
import 'package:mobile/features/auth/repository/authRepository.dart';
import 'package:mobile/features/auth/screen/forgot_password.dart';
import 'package:mobile/features/auth/screen/signUp.dart';
import 'package:mobile/features/main/bottom_navigation_bar.dart';
import 'package:mobile/network/apiClient.dart';
import 'package:mobile/shared/widgets/appButton.dart';
import 'package:mobile/shared/widgets/appTextField.dart';
import 'package:mobile/shared/widgets/appToast.dart';
import 'package:mobile/shared/widgets/textButton.dart';
import 'package:mobile/storage/secure_storage.dart';

final apiClientProvider = Provider<ApiClient>((ref) {
  return ApiClient();
});

final authRepositoryProvider = Provider<AuthRepository>((ref) {
  final apiClient = ref.watch(apiClientProvider);
  return AuthRepository(apiClient);
});

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  bool _rememberMe = false;
  bool _isObscure = true;

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

  Future<void> handleGoogleLogin() async {
    try {
      final account = await GoogleAuthService.signIn();

      if (account == null) {
        return;
      }

      final authentication = account.authentication;

      final idToken = authentication.idToken;

      if (idToken == null) {
        throw Exception('Google ID token not found');
      }

      debugPrint('Google ID Token received: $idToken');

      final request = GoogleSignInRequest(idToken: idToken);
      final authRepository = ref.read(authRepositoryProvider);
      final response = await authRepository.googleLogin(request);
      if (!mounted) return;
      AppToast.success('Login successful');
      debugPrint('Login successful');
      debugPrint(response.accessToken);
      debugPrint(response.tokenType);
      debugPrint(response.refreshToken);
      debugPrint(response.expiresIn.toString());
      await SecureStorage.saveIsLoggedIn(true);
      await SecureStorage.saveAccessToken(response.accessToken);
      await SecureStorage.saveTokenType(response.tokenType);
      await SecureStorage.saveRefreshToken(response.refreshToken);
      await SecureStorage.saveExpiresIn(response.expiresIn);
      await SecureStorage.saveIsFirstLaunch(false);

      if (!mounted) return;

      Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (context) => const MainScreen()),
        (route) => false,
      );
    } catch (e) {
      debugPrint('Google Sign-In failed: $e');
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
      final request = LoginRequest(
        email: _emailController.text.trim(),
        password: _passwordController.text,
      );

      try {
        final authRepository = ref.read(authRepositoryProvider);
        final response = await authRepository.login(request);

        if (!mounted) return;

        AppToast.success('Login successful');

        debugPrint('Login successful');
        await SecureStorage.saveIsLoggedIn(true);
        await SecureStorage.saveAccessToken(response.accessToken);
        await SecureStorage.saveTokenType(response.tokenType);
        await SecureStorage.saveRefreshToken(response.refreshToken);
        await SecureStorage.saveExpiresIn(response.expiresIn);
        await SecureStorage.saveIsFirstLaunch(false);

        if (!mounted) return;

        Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (context) => const MainScreen()),
          (route) => false,
        );
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
                    onPressed: () {
                      handleGoogleLogin();
                    },
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
