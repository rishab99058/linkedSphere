import 'package:flutter_test/flutter_test.dart';
import 'package:mobile/main.dart';

void main() {
  testWidgets('LinkedSphereApp displays splash screen title', (
    WidgetTester tester,
  ) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const LinkedSphereApp());

    // Verify that the splash screen title 'LinkedSphere' is present.
    expect(find.text('LinkedSphere'), findsOneWidget);
  });
}
