package com.example.testingappkotlin.classes

import android.util.Log
import javax.inject.Inject

interface UserRepository{
    fun saveUser(email: String, password: String)
}

class SQLRepository @Inject constructor(): UserRepository{
    override fun saveUser(email: String, password: String){
        Log.d("TAG","User Saved in SQL")
    }
}

class FirebaseRepository: UserRepository{
    override fun saveUser(email: String, password: String){
        Log.d("TAG","User Saved in Firebase")
    }
}