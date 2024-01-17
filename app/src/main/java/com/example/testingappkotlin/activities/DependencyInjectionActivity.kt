package com.example.testingappkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.testingappkotlin.classes.EmailService
import com.example.testingappkotlin.classes.UserRegistrationService
import com.example.testingappkotlin.classes.UserRepository
import com.example.testingappkotlin.R
import com.example.testingappkotlin.interfaces.DaggerUserRegistrationComponent
import com.example.testingappkotlin.interfaces.UserRegistrationComponent
import javax.inject.Inject

class DependencyInjectionActivity : AppCompatActivity() {

    @Inject
    lateinit var userRegistrationService: UserRegistrationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependency_injection)

        //Manual dependency Injection

//        val userRepository = UserRepository()
//        val emailService = EmailService()
//
//        val userRegistrationService = UserRegistrationService(userRepository,emailService)
//        userRegistrationService.registerUser("fahad@gmail.com", "12345678")

        //Using @Component and @Inject

//        val userRegistrationService = DaggerUserRegistrationComponent.builder().build().getUserRegistrationService()
//        userRegistrationService.registerUser("fahad@gmail.com", "12345678")

        //Field Injection

        val component = DaggerUserRegistrationComponent.builder().build()
        component.inject(this)
        userRegistrationService.registerUser("fahad@gmail.com", "12345678")

    }
}