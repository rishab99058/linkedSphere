class ProfileResponse {
  final String id;
  final String authId;
  final String fullName;
  final String headline;
  final String about;
  final String? profilePictureUrl;
  final String? backgroundImageUrl;
  final String location;
  final String industry;
  final String? websiteUrl;
  final String email;
  final String? phoneNumber;
  final List<String> roles;

  ProfileResponse({
    required this.id,
    required this.authId,
    required this.fullName,
    required this.headline,
    required this.about,
    this.profilePictureUrl,
    this.backgroundImageUrl,
    required this.location,
    required this.industry,
    this.websiteUrl,
    required this.email,
    this.phoneNumber,
    required this.roles,
  });
  factory ProfileResponse.fromJson(Map<String, dynamic> json) {
    return ProfileResponse(
      id: json['id'] as String,
      authId: json['authId'] as String,
      fullName: json['fullName'] as String,
      headline: json['headline'] as String,
      about: json['about'] as String,
      profilePictureUrl: json['profilePictureUrl'] as String?,
      backgroundImageUrl: json['backgroundImageUrl'] as String?,
      location: json['location'] as String,
      industry: json['industry'] as String,
      websiteUrl: json['websiteUrl'] as String?,
      email: json['email'] as String,
      phoneNumber: json['phoneNumber'] as String?,
      roles:
          (json['roles'] as List<dynamic>?)
              ?.map((role) => role.toString())
              .toList() ??
          [],
    );
  }
}
