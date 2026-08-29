import 'package:mobile/config/enviroment.dart';

class AppConfig {
  AppConfig._();

  static const String _environment = String.fromEnvironment(
    'ENV',
    defaultValue: 'dev',
  );

  static Environment get environment {
    switch (_environment.toLowerCase()) {
      case 'qa':
        return Environment.qA;

      case 'prod':
      case 'production':
        return Environment.prod;

      case 'staging':
        return Environment.staging;

      default:
        return Environment.dev;
    }
  }

  static String get baseUrl {
    switch (environment) {
      case Environment.dev:
        return 'https://linksphere-gateway-service-190262577212.asia-south2.run.app';

      case Environment.qA:
        return 'https://linksphere-gateway-service-190262577212.asia-south2.run.app';

      case Environment.prod:
        return 'YOUR_PROD_API_URL';

      case Environment.staging:
        return 'https://linksphere-gateway-service-190262577212.asia-south2.run.app';
    }
  }
}
