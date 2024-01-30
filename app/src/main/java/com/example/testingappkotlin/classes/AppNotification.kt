package com.example.testingappkotlin.classes

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.testingappkotlin.R

class AppNotification : Application() {

    companion object {
        const val notificationChannelId: String = "notification_channel_id"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "Notification Channel",
                NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "Notification show when user register yourself in application"

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }else {
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Create a notification using the deprecated Notification constructor
            val notification: Notification = NotificationCompat.Builder(applicationContext)
                    .setContentTitle("Notification Title")
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setWhen(System.currentTimeMillis())
                    .build()

            // Show the notification
            notificationManager.notify(1, notification)
        }

    }

}