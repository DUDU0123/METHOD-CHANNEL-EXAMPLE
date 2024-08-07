package com.example.method_channel

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.media.RingtoneManager
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity(){
    private val BATTERY_CHANNEL = "batterychanneldudu";
    private val channelNameOfRing = "ringtonegiver"
    private lateinit var channel: MethodChannel;
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelNameOfRing)
        .setMethodCallHandler { call, result ->
            when (call.method){

                "getAllRingtones" -> {
                    val ringtones = getAllRingtones(this)
                    result.success(ringtones);
                }
            }
        }
    }

    private fun getBatteryLevel(): Int {
        var batteryLevel = -1
        batteryLevel = if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }

    private fun getAllRingtones(context: Context): List<String> {
        val manager = RingtoneManager(context)
        manager.setType(RingtoneManager.TYPE_NOTIFICATION)
        val cursor: Cursor = manager.cursor
        val list: MutableList<String> = mutableListOf()
        while (cursor.moveToNext()) {
            val notificationTitle: String = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            list.add(notificationTitle)
        }
        return list
    }

}
