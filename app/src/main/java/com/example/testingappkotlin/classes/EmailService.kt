package com.example.testingappkotlin.classes

import android.util.Log
import javax.inject.Inject

interface NotificationService{
    fun send(to: String, from: String, body: String)
}

class EmailService @Inject constructor() : NotificationService{
    override fun send(to: String, from: String, body: String){
        Log.d("TAG", "Email Sent")
    }
}

class MessageService: NotificationService{
    override fun send(to: String, from: String, body: String){
        Log.d("TAG", "Message Sent")
    }
}