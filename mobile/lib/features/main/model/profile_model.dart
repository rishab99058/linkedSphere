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
  final String websiteUrl;
  final String email;
  final String phoneNumber;
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
    required this.websiteUrl,
    required this.email,
    required this.phoneNumber,
    required this.roles,
  });

  factory ProfileResponse.fromJson(Map<String, dynamic> json) {
    return ProfileResponse(
      id: json['id'],
      authId: json['authId'],
      fullName: json['fullName'],
      headline: json['headline'],
      about: json['about'],
      profilePictureUrl: json['profilePictureUrl'],
      backgroundImageUrl: json['backgroundImageUrl'],
      location: json['location'],
      industry: json['industry'],
      websiteUrl: json['websiteUrl'],
      email: json['email'],
      phoneNumber: json['phoneNumber'],
      roles: List<String>.from(json['roles'] ?? []),
    );
  }
}
