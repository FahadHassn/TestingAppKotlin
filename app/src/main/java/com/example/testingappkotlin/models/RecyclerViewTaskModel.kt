package com.example.testingappkotlin.models

data class RecyclerViewTaskModel(
    val name: String,
    val message: String,
    val time: String,
    val status: Boolean,
    val image: List<Int>?
)
