package com.example.testingappkotlin.Classes

class UserRegistrationService(private val userRepository: UserRepository, private val emailService: EmailService) {
    fun registerUser(email: String, password: String){
        userRepository.saveUser(email,password)
        emailService.send(email,"fahad@gmail.com","User Registered")
    }
}


/**
 * Unit Testing
 * Single Responsibility
 * Lifetime of these object
 * Extensible
 */