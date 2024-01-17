package com.example.testingappkotlin.interfaces

import com.example.testingappkotlin.activities.DependencyInjectionActivity
import com.example.testingappkotlin.classes.NotificationServiceModule
import com.example.testingappkotlin.classes.UserRegistrationService
import com.example.testingappkotlin.classes.UserRepositoryModule
import dagger.Component

@Component(modules = [UserRepositoryModule::class, NotificationServiceModule::class])
interface UserRegistrationComponent {

//    fun getUserRegistrationService(): UserRegistrationService

    fun inject(dependencyInjectionActivity: DependencyInjectionActivity)

}