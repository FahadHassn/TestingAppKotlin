package com.example.testingappkotlin.activities

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testingappkotlin.R
import com.example.testingappkotlin.classes.AppNotification
import com.example.testingappkotlin.databinding.ActivityBottomNavigationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val intent = intent
        val name = intent.getStringExtra("name")

        val activityIntent = Intent(this,BottomNavigationActivity::class.java)
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val contentIntent: PendingIntent  = PendingIntent.getActivity(
            this,0,activityIntent, PendingIntent.FLAG_IMMUTABLE)

        val actionActivityIntent = Intent(this,BottomNavigationActivity::class.java)
        actionActivityIntent.putExtra("key","Welcome to Testing app kotlin")
        actionActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val actionIntent: PendingIntent  = PendingIntent.getActivity(
            this,0,actionActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //Notification
        val notification: Notification = NotificationCompat.Builder(this, AppNotification.notificationChannelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Hi $name")
            .setContentText("Welcome to Testing app kotlin")
            .setContentIntent(contentIntent)
            .addAction(R.drawable.baseline_notifications_24,"Message",actionIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()
//        notificationManager.notify(1, notification)
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notification)

    }
}