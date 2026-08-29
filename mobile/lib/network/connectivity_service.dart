import 'package:connectivity_plus/connectivity_plus.dart';

class ConnectivityService {
  ConnectivityService(this._connectivity);

  final Connectivity _connectivity;

  Future<bool> isConnected() async {
    final result = await _connectivity.checkConnectivity();
    return result.any((r) => r != ConnectivityResult.none);
  }

  Stream<bool> get connectionStream {
    return _connectivity.onConnectivityChanged.map(
      (results) => results.any((r) => r != ConnectivityResult.none),
    );
  }
}
