class ProfileNotFoundException implements Exception {
  final String message;

  ProfileNotFoundException([this.message = 'Profile has not been set up']);

  @override
  String toString() => message;
}
