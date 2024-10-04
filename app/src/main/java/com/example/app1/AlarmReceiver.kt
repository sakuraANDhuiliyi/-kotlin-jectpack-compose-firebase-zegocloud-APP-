package com.example.app1

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat

class AlarmReceiver:BroadcastReceiver() {
    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context?, intent: Intent?) {
        val message=intent?.getStringExtra("message")?: return
        //Toast.makeText(context,"Alarm:$message",Toast.LENGTH_LONG).show()
        //println("Alarm:$message")
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.first)
            .setContentTitle("闹钟提醒")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)


        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Channel",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), null)
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 1000, 500, 1000,500,1000,500,1000)
        }
        notificationManager.createNotificationChannel(channel)


        notificationManager.notify(1, notificationBuilder.build())

    }
}