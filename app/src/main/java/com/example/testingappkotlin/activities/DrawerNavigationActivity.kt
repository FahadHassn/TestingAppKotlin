package com.example.testingappkotlin.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.testingappkotlin.R
import com.example.testingappkotlin.classes.AppNotification
import com.example.testingappkotlin.databinding.ActivityDrawerNavigationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DrawerNavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawerNavigation.toolbar)

//        binding.appBarDrawerNavigation.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation_view)
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_drawer_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_gallery,
                R.id.navigation_slideshow,
                R.id.navigation_notifications
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)

        val intent = intent
        val name = intent.getStringExtra("name")
        if (name != null){
            askNotificationPermission()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_navigation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            notification()
        } else {
            Toast.makeText(
                this,
                "Notifications not showing you",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                notification()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionExplanationDialog()
            } else {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }else{
            notification()
        }
    }

    private fun notification() {

        val intent = intent
        val name = intent.getStringExtra("name")

        val activityIntent = Intent(this, DrawerNavigationActivity::class.java)
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val contentIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val actionActivityIntent = Intent(this, DrawerNavigationActivity::class.java)
        actionActivityIntent.putExtra("key", "Welcome to Testing app kotlin")
        actionActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val actionIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, actionActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

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

    private fun showPermissionExplanationDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("This app requires notification permission to show important updates and messages.")
            .setPositiveButton("Grant Permission") { _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Handle the case where the user declines permission
                Toast.makeText(this, "Notifications won't be shown", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

}