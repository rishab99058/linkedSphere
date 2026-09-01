class ProfileRequest {
  final String? userId;

  final String fullName;
  final String headline;
  final String about;
  final String? profilePictureUrl;
  final String? backgroundImageUrl;
  final String location;
  final String industry;
  final String? websiteUrl;

  ProfileRequest({
    this.userId,
    required this.fullName,
    required this.headline,
    required this.about,
    this.profilePictureUrl,
    this.backgroundImageUrl,
    required this.location,
    required this.industry,
    this.websiteUrl,
  });

  Map<String, dynamic> toJson() {
    return {
      if (userId != null) 'userId': userId,
      'fullName': fullName,
      'headline': headline,
      'about': about,
      'profilePictureUrl': profilePictureUrl,
      'backgroundImageUrl': backgroundImageUrl,
      'location': location,
      'industry': industry,
      'websiteUrl': websiteUrl,
    };
  }
}
