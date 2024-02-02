package com.example.testingappkotlin.services

import android.app.Notification
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.testingappkotlin.R
import com.example.testingappkotlin.classes.AppNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMMessageReceiverService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MyTag", "onMessageReceived: called")

        Log.d("MyTag", "onMessageReceived: Message received from: ${message.from}")

        if (message.notification != null){
            val title: String = message.notification?.title.toString()
            val body: String = message.notification?.body.toString()

            val notification: Notification = NotificationCompat.Builder(this,AppNotification.notificationChannelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(body)
                .build()

            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1002,notification)
        }

        if (message.data.isNotEmpty()){
            Log.d("MyTag", "onMessageReceived: Data: ${message.data}")
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Log.d("MyTag", "onDeletedMessages: called")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyTag", "onNewToken: called")
    }


}