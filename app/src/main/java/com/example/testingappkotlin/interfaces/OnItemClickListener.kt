package com.example.testingappkotlin.interfaces

import com.example.testingappkotlin.models.SectionModel

interface OnItemClickListener {
    fun onEditClicked(sectionModel: SectionModel, position: Int)
    fun onDeleteClicked(sectionModel: SectionModel)

}