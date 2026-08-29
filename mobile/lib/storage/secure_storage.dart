import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorage {
  SecureStorage._();

  static const FlutterSecureStorage _storage = FlutterSecureStorage();

  // ============================================================
  // KEYS
  // ============================================================

  // ---------------- AUTHENTICATION ----------------

  static const String accessTokenKey = 'access_token';
  static const String refreshTokenKey = 'refresh_token';
  static const String tokenTypeKey = 'token_type';
  static const String expiresInKey = 'expires_in';

  // ---------------- SESSION ----------------

  static const String isLoggedInKey = 'is_logged_in';

  // ---------------- APP STATE ----------------

  static const String isFirstLaunchKey = 'is_first_launch';

  // ---------------- REMEMBER ME ----------------

  static const String rememberMeKey = 'remember_me';
  static const String savedEmailKey = 'saved_email';
  static const String savedPasswordKey = 'saved_password';

  // ============================================================
  // ACCESS TOKEN
  // ============================================================

  static Future<void> saveAccessToken(String token) async {
    await _storage.write(key: accessTokenKey, value: token);
  }

  static Future<String?> getAccessToken() async {
    return await _storage.read(key: accessTokenKey);
  }

  static Future<void> clearAccessToken() async {
    await _storage.delete(key: accessTokenKey);
  }

  // ============================================================
  // REFRESH TOKEN
  // ============================================================

  static Future<void> saveRefreshToken(String token) async {
    await _storage.write(key: refreshTokenKey, value: token);
  }

  static Future<String?> getRefreshToken() async {
    return await _storage.read(key: refreshTokenKey);
  }

  static Future<void> clearRefreshToken() async {
    await _storage.delete(key: refreshTokenKey);
  }

  // ============================================================
  // TOKEN TYPE
  // ============================================================

  static Future<void> saveTokenType(String tokenType) async {
    await _storage.write(key: tokenTypeKey, value: tokenType);
  }

  static Future<String?> getTokenType() async {
    return await _storage.read(key: tokenTypeKey);
  }

  static Future<void> clearTokenType() async {
    await _storage.delete(key: tokenTypeKey);
  }

  // ============================================================
  // EXPIRES IN
  // ============================================================

  static Future<void> saveExpiresIn(int expiresIn) async {
    await _storage.write(key: expiresInKey, value: expiresIn.toString());
  }

  static Future<int?> getExpiresIn() async {
    final value = await _storage.read(key: expiresInKey);

    if (value == null) {
      return null;
    }

    return int.tryParse(value);
  }

  static Future<void> clearExpiresIn() async {
    await _storage.delete(key: expiresInKey);
  }

  // ============================================================
  // LOGIN STATE
  // ============================================================

  static Future<void> saveIsLoggedIn(bool value) async {
    await _storage.write(key: isLoggedInKey, value: value.toString());
  }

  static Future<bool> getIsLoggedIn() async {
    final value = await _storage.read(key: isLoggedInKey);

    return value == 'true';
  }

  static Future<void> clearIsLoggedIn() async {
    await _storage.delete(key: isLoggedInKey);
  }

  // ============================================================
  // FIRST LAUNCH
  // ============================================================

  static Future<void> saveIsFirstLaunch(bool value) async {
    await _storage.write(key: isFirstLaunchKey, value: value.toString());
  }

  static Future<bool> getIsFirstLaunch() async {
    final value = await _storage.read(key: isFirstLaunchKey);

    // No value means app is being opened for the first time.
    if (value == null) {
      return true;
    }

    return value == 'true';
  }

  static Future<void> clearIsFirstLaunch() async {
    await _storage.delete(key: isFirstLaunchKey);
  }

  // ============================================================
  // REMEMBER ME
  // ============================================================

  static Future<void> saveRememberMe(bool value) async {
    await _storage.write(key: rememberMeKey, value: value.toString());
  }

  static Future<bool> getRememberMe() async {
    final value = await _storage.read(key: rememberMeKey);

    return value == 'true';
  }

  static Future<void> clearRememberMe() async {
    await _storage.delete(key: rememberMeKey);
  }

  // ============================================================
  // SAVED EMAIL
  // ============================================================

  static Future<void> saveSavedEmail(String email) async {
    await _storage.write(key: savedEmailKey, value: email);
  }

  static Future<String?> getSavedEmail() async {
    return await _storage.read(key: savedEmailKey);
  }

  static Future<void> clearSavedEmail() async {
    await _storage.delete(key: savedEmailKey);
  }

  // ============================================================
  // SAVED PASSWORD
  // ============================================================

  static Future<void> saveSavedPassword(String password) async {
    await _storage.write(key: savedPasswordKey, value: password);
  }

  static Future<String?> getSavedPassword() async {
    return await _storage.read(key: savedPasswordKey);
  }

  static Future<void> clearSavedPassword() async {
    await _storage.delete(key: savedPasswordKey);
  }

  // ============================================================
  // CLEAR AUTH SESSION
  // ============================================================

  // Use this when user logs out.
  //
  // This clears authentication/session information
  // but DOES NOT clear Remember Me credentials.
  static Future<void> clearSession() async {
    await _storage.delete(key: accessTokenKey);

    await _storage.delete(key: refreshTokenKey);

    await _storage.delete(key: tokenTypeKey);

    await _storage.delete(key: expiresInKey);

    await _storage.delete(key: isLoggedInKey);
  }

  // ============================================================
  // CLEAR REMEMBERED CREDENTIALS
  // ============================================================

  static Future<void> clearRememberedCredentials() async {
    await _storage.delete(key: rememberMeKey);

    await _storage.delete(key: savedEmailKey);

    await _storage.delete(key: savedPasswordKey);
  }

  // ============================================================
  // CLEAR EVERYTHING
  // ============================================================

  // Use this only when you intentionally want to
  // completely reset the local app state.
  static Future<void> clearAll() async {
    await _storage.deleteAll();
  }
}
