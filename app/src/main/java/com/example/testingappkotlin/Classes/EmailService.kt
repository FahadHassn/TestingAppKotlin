package com.example.testingappkotlin.Classes

import android.util.Log

class EmailService {
    fun send(to: String, from: String, body: String){
        Log.d("TAG", "Email Sent")
    }
}