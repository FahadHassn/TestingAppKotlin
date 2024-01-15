package com.example.testingappkotlin.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testingappkotlin.Classes.EmailService
import com.example.testingappkotlin.Classes.UserRegistrationService
import com.example.testingappkotlin.Classes.UserRepository
import com.example.testingappkotlin.R

class DependencyInjectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_injection)

        //Manual dependency Injection

        val userRepository = UserRepository()
        val emailService = EmailService()

        val userRegistrationService = UserRegistrationService(userRepository,emailService)
        userRegistrationService.registerUser("fahad@gmail.com", "12345678")

    }
}