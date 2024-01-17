package com.example.testingappkotlin.classes

import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class UserRepositoryModule {
//    @Provides
//    fun getFirebaseRepository(): UserRepository{
//        return FirebaseRepository()
//    }

    @Binds
    abstract fun getSQLRepository(sqlRepository: SQLRepository): UserRepository

}