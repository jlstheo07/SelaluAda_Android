package com.example.selaluada.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.selaluada.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService  : FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "From: $remoteMessage.from}")

        remoteMessage.data.isNotEmpty().let {
            Log.d("FCM", "Message data payload: ${remoteMessage.data}")
            showNotification(remoteMessage.data["title"], remoteMessage.data["body"])
        }

        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
    }

    private fun showNotification(title: String?, message: String?) {
        val channelId = "default_channel_id"
        val channelName = "Default Channel"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Buat channel (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo) // ganti dengan ikonmu
            .setContentTitle(title ?: "Notifikasi")
            .setContentText(message ?: "")
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }
}
