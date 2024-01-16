package com.example.testingappkotlin.interfaces

import com.example.testingappkotlin.classes.UserRegistrationService
import dagger.Component

@Component
interface UserRegistrationComponent {
    fun getUserRegistrationService(): UserRegistrationService
}