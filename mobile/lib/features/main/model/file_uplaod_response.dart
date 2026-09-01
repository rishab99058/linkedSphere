class FileUploadResponse {
  final String fileId;
  final String fileName;
  final String url;
  final String fileType;
  final int fileSize;

  FileUploadResponse({
    required this.fileId,
    required this.fileName,
    required this.url,
    required this.fileType,
    required this.fileSize,
  });

  factory FileUploadResponse.fromJson(Map<String, dynamic> json) {
    return FileUploadResponse(
      fileId: json['fileId'] as String,
      fileName: json['fileName'] as String,
      url: json['url'] as String,
      fileType: json['fileType'] as String,
      fileSize: json['fileSize'] as int,
    );
  }
}
