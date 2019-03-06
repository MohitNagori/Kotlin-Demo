package com.example.mohit.notificationdemo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log

import com.example.mohit.notificationdemo.R
import com.example.mohit.notificationdemo.activity.MainActivity
import com.example.mohit.notificationdemo.util.NotificationID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Mohit.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        var body: String? = ""

        Log.d(TAG, remoteMessage!!.from)

        if (remoteMessage.notification != null) {
            body = remoteMessage.notification.body
            Log.d(TAG, body)
        }
        displayNotification(remoteMessage.from, body!!)
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

    private fun displayNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        initChannels(applicationContext)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.drawable.ic_notifications_none_white_24dp)
                .setAutoCancel(true)
                .setContentTitle("Push Notification")
                .setContentText(body)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NotificationID.id, notificationBuilder.build())
    }

    companion object {
        private val TAG = "1234"
    }
}
