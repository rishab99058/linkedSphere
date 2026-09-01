import 'package:flutter/material.dart';

class CreateUpdateProfileScreen extends StatefulWidget {
  const CreateUpdateProfileScreen({super.key});

  @override
  State<CreateUpdateProfileScreen> createState() =>
      _CreateUpdateProfileScreenState();
}

class _CreateUpdateProfileScreenState extends State<CreateUpdateProfileScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Create Update Profile',
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                const SizedBox(height: 16),
                TextField(decoration: const InputDecoration(labelText: 'Name')),
                const SizedBox(height: 16),
                TextField(
                  decoration: const InputDecoration(labelText: 'Email'),
                ),
                const SizedBox(height: 16),
                TextField(
                  decoration: const InputDecoration(labelText: 'Phone Number'),
                ),
                const SizedBox(height: 16),
                ElevatedButton(onPressed: () {}, child: const Text('Save')),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
