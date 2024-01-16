package com.example.testingappkotlin.interfaces

import com.example.testingappkotlin.modals.SectionModel

interface OnItemClickListener {
    fun onEditClicked(sectionModel: SectionModel, name: String, date: String, lastName: String)
    fun onDeleteClicked(sectionModel: SectionModel)

}