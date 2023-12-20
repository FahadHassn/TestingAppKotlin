package com.example.testingappkotlin.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.Modals.Section
import com.example.testingappkotlin.Modals.SectionModel
import com.example.testingappkotlin.R

class RecyclerSectionAdapter(private val context: Context, private val list: List<Section>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_section_header_design, parent, false)
                SectionHeaderViewHolder(itemView)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_section_design, parent, false)
                SectionViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return list.sumOf { it.sections.size + 1 }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val sectionHeaderViewHolder = holder as SectionHeaderViewHolder
                val section = getSectionForPosition(position)
                sectionHeaderViewHolder.bind(section.title)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val sectionViewHolder = holder as SectionViewHolder
                val section = getItem(position)
                sectionViewHolder.bind(section)
            }
        }
    }

    private fun getSectionForPosition(position: Int): Section {
        var adjustedPosition = position
        for (section in list) {
            if (adjustedPosition == 0) {
                return section
            } else if (adjustedPosition <= section.sections.size) {
                // Move to the next section
                adjustedPosition -= section.sections.size + 1
            } else {
                // Move to the next section
                adjustedPosition -= section.sections.size + 1
            }
        }
        throw IndexOutOfBoundsException("Invalid position")
    }

    private fun getItem(position: Int): SectionModel {
        var adjustedPosition = position
        for (section in list) {
            if (adjustedPosition < section.sections.size + 1) {
                if (adjustedPosition == 0) {
                    adjustedPosition++
                } else if (adjustedPosition <= section.sections.size){
                    return section.sections[adjustedPosition - 1]
                }
            } else {
                // Move to the next section
                adjustedPosition -= section.sections.size + 1
            }
        }
        throw IndexOutOfBoundsException("Invalid position")
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSectionHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    private fun isSectionHeader(position: Int): Boolean {
        var index = 0
        for (section in list) {
            if (position == index) {
                return true
            }
            index += section.sections.size + 1
        }
        return false
    }

    inner class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val txtName: TextView = view.findViewById(R.id.sectionDesignName)
        private val txtDate: TextView = view.findViewById(R.id.sectionDesignDate)

        fun bind(sectionModel: SectionModel){
            txtName.text = sectionModel.name
            txtDate.text = sectionModel.date
        }

    }

    inner class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val txtHeader: TextView = itemView.findViewById(R.id.sectionHeader)
        fun bind(header: String){
            txtHeader.text = header
        }
    }



}