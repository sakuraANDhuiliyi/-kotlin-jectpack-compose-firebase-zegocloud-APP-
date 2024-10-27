package com.example.app1.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context:Context
): AlarmScheduler {
    private  val alarmManager = context.getSystemService(AlarmManager::class.java)
    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message", item.message)
        }
      try {
          alarmManager.setExactAndAllowWhileIdle(
              AlarmManager.RTC_WAKEUP,
              item.time.atZone(ZoneId.systemDefault()).toEpochSecond()*1000,
              PendingIntent.getBroadcast(
                  context,
                  item.hashCode(),
                  intent,
                  PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
              )
          )
      } catch (e: SecurityException) {
          Toast.makeText(context, "Exact alarm permission is not granted.", Toast.LENGTH_LONG).show()
      }
    }

    override fun cancel(item: AlarmItem) {
       alarmManager.cancel(
           PendingIntent.getBroadcast(
               context,
               item.hashCode(),
               Intent(context, AlarmReceiver::class.java),
               PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
           )
       )
    }
}