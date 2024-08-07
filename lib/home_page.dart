import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:just_audio/just_audio.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  // static const batteryChannel = MethodChannel('batterychanneldudu');
  String batteryLevel = '0';
  static const ringtoneChannel = MethodChannel('ringtonegiver');
  List notificationTones = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [
          ElevatedButton(
            onPressed: () async {
              List<dynamic> result =
                  await ringtoneChannel.invokeMethod('getAllRingtones');
              notificationTones = result;
              setState(() {});
            },
            child: const Text(
              "Get tones",
              style: TextStyle(
                color: Colors.green,
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ],
      ),
      body: ListView.separated(
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
        itemBuilder: (context, index) {
          final notifyTone = notificationTones[index];
          return ListTile(
            onTap: () async {
              final player = AudioPlayer();
              await player.pause();
              await player.setFilePath(
                  "/system/media/audio/notifications/$notifyTone.ogg");
              await player.play();
            },
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
            title: Text(notificationTones[index]),
            tileColor: Colors.deepPurple,
          );
        },
        separatorBuilder: (context, index) => const SizedBox(height: 10),
        itemCount: notificationTones.length,
      ),
    );
  }
}
