package com.example.testingappkotlin.modals

data class RecyclerViewTaskModal(
    val name: String,
    val message: String,
    val time: String,
    val status: Boolean,
    val image: List<Int>?
)
