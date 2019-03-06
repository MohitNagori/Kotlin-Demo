package com.example.mohit.notificationdemo.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.mohit.notificationdemo.util.NotificationID
import com.example.mohit.notificationdemo.R
import com.example.mohit.notificationdemo.service.CounterService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, CounterService::class.java)
        startService(intent)
    }

    internal fun initChannels(context: Context) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("default",
                "NOTIFICATION_DEMO_CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Notification Demo Channel description"
        notificationManager?.createNotificationChannel(channel)
    }

    fun displayNotification(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        initChannels(applicationContext)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.drawable.ic_notifications_none_white_24dp)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Local Notification")
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NotificationID.id, notificationBuilder.build())
    }
}
