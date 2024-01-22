package com.example.testingappkotlin.models

data class UserLoginModel(val name: String = "",
                          val email: String = "",
                          val phone: String = "",
                          val password: String = "",
                          val id: String? = null
)
