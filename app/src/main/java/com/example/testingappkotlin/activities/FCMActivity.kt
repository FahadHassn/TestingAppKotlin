package com.example.testingappkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.ActivityFcmactivityBinding

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
        }
    }
}