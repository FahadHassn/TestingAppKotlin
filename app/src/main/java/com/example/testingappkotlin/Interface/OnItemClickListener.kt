package com.example.testingappkotlin.Interface

import com.example.testingappkotlin.Modals.SectionModel

interface OnItemClickListener {
    fun onEditClicked(sectionModel: SectionModel, name: String, date: String, lastName: String)
    fun onDeleteClicked(sectionModel: SectionModel)

}