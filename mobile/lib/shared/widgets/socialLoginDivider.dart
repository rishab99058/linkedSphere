import 'package:flutter/material.dart';

class SocialLoginDivider extends StatelessWidget {
  const SocialLoginDivider({super.key, this.text = 'OR'});

  final String text;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        const Expanded(child: Divider()),

        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Text(text),
        ),

        const Expanded(child: Divider()),
      ],
    );
  }
}
