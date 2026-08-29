import 'package:google_sign_in/google_sign_in.dart';

class GoogleAuthService {
  GoogleAuthService._();

  static final GoogleSignIn _googleSignIn = GoogleSignIn.instance;

  static Future<void> initialize() async {
    await _googleSignIn.initialize(
      clientId:
          '190262577212-mkpofmng8u4csgijo38s0ev8fs2j6jmc.apps.googleusercontent.com',
    );
  }

  static Future<GoogleSignInAccount?> signIn() async {
    try {
      return await _googleSignIn.authenticate();
    } on GoogleSignInException catch (e) {
      print('Google Sign-In failed: ${e.code}');
      print(e.description);
      rethrow;
    }
  }

  static Future<void> signOut() async {
    await _googleSignIn.signOut();
  }
}
