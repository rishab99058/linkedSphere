import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mobile/app/app.dart';

void main() {
  testWidgets('LinkedSphereApp displays splash screen logo', (
    WidgetTester tester,
  ) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(const LinkedSphereApp());

    // Verify that the splash screen shows an Image widget.
    expect(find.byType(Image), findsOneWidget);
  });
}

