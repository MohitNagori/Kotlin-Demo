package com.example.mohit.notificationdemo.service

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

import com.example.mohit.notificationdemo.util.NotificationID
import com.example.mohit.notificationdemo.R

/**
 * Created by Mohit on 11/8/2017.
 */

class CounterService : IntentService("CounterService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onHandleIntent(intent: Intent?) {
        for (count in 1..3) {
            //ten sec is 10*1000
            try {
                Thread.sleep((10 * 1000).toLong())
                displayNotification(count)
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
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


    private fun displayNotification(count: Int) {
        initChannels(applicationContext)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.drawable.ic_notifications_none_white_24dp)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Background Service Notification $count")

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NotificationID.id, notificationBuilder.build())
    }
}