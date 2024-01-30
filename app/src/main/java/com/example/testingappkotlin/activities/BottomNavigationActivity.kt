package com.example.testingappkotlin.activities

import android.app.Notification
import android.app.NotificationManager
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

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //Notification
        val notification: Notification = NotificationCompat.Builder(this, AppNotification.notificationChannelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Hi $name")
            .setContentText("Welcome to Testing app kotlin")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
//        notificationManager.notify(1, notification)
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notification)

    }
}