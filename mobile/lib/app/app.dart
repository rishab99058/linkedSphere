import 'package:flutter/material.dart';

class LinkedSphereApp extends StatelessWidget {
  const LinkedSphereApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'LinkedSphere',
      home: const Scaffold(body: Center(child: Text('LinkedSphere'))),
    );
  }
}
