import 'package:flutter/material.dart';
import 'package:method_channel/home_page.dart';

class RootWidgetPage extends StatelessWidget {
  const RootWidgetPage({super.key});

  @override
  Widget build(BuildContext context) {
    return  MaterialApp(
      theme: ThemeData(
        useMaterial3: true
      ),
      debugShowCheckedModeBanner: false,
      home: const HomePage(),
    );
  }
}