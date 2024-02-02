package com.example.testingappkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityFcmactivityBinding
import com.google.firebase.messaging.FirebaseMessaging

class FCMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFcmactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //get and set extras from FCM notification data payload
            if (intent != null && intent.hasExtra("key")) {
                for (key in intent.extras!!.keySet()) {
                    val value = intent.extras?.getString(key)
                    if (value != null) {
                        if (key.equals("key") || key.equals("name")){ //this key and name add keys in FCM firebase
                            binding.getExtrasNotificationData.append(value+"\n")
                        }
                    }
                }
            }

            //Retrieve FCM registration token Send message to single device
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Get the FCM registration token
                        val token = task.result
                        // Log the token
                        Log.d("myTAG", "Token: $token")
                        // Set a text view or some UI element to indicate that the token has been generated
                        fcmToken.text = "Token Generated"
                    } else {
                        // Handle the case where token generation failed
                        fcmToken.text = "Token generation failed"
                    }
                }

        }
    }
}