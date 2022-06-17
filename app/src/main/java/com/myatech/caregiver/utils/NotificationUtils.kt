package com.myatech.caregiver.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.myatech.caregiver.MainActivity
import com.myatech.caregiver.R

class NotificationUtils(base:Context):ContextWrapper(base) {
    val MYCHANNEL_ID="App Alert Notification ID"
    val MYCHANNEL_NAME="App Alert Notification"
    private var manager:NotificationManager?=null
    init {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannels(){
        val channel = NotificationChannel(MYCHANNEL_ID,MYCHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)
        getManager().createNotificationChannel(channel)
    }
    fun getManager():NotificationManager{
        if(manager==null)manager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        return manager as NotificationManager
    }
    fun getNotificationBuilder():NotificationCompat.Builder{
        val intent = Intent(this,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return NotificationCompat.Builder(applicationContext,MYCHANNEL_ID)
            .setContentTitle("Alarm!")
            .setContentText("AlarmManager is working.")
            .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
    }

}