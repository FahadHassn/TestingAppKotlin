package com.example.testingappkotlin.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.R

class ExplicitIntentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit_intent)
    }
}