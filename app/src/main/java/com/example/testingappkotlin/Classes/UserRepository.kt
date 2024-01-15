package com.example.testingappkotlin.Classes

import android.util.Log

class UserRepository {
    fun saveUser(email: String, password: String){
        Log.d("TAG","User Saved in DB")
    }
}