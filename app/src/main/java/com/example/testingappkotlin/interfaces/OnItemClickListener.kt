package com.example.testingappkotlin.interfaces

import com.example.testingappkotlin.models.SectionModel

interface OnItemClickListener {
    fun onEditClicked(sectionModel: SectionModel, name: String, date: String, lastName: String)
    fun onDeleteClicked(sectionModel: SectionModel)

}