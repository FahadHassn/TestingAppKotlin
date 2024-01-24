package com.example.testingappkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.interfaces.OnItemClickListener
import com.example.testingappkotlin.models.Section
import com.example.testingappkotlin.models.SectionModel
import com.example.testingappkotlin.R

class RecyclerSectionAdapter(private val context: Context, private var list: List<Section>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val itemViewTypeHeader = 0
    private val itemViewTypeItem = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            itemViewTypeHeader -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_section_header_design, parent, false)
                SectionHeaderViewHolder(itemView)
            }
            itemViewTypeItem -> {
                val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_section_design, parent, false)
                SectionViewHolder(view,)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(lists: List<Section>){
        this.list= lists
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.sumOf { it.sections.size+1 }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            itemViewTypeHeader -> {
                val sectionHeaderViewHolder = holder as SectionHeaderViewHolder
                val sectionHeader = getSectionForPosition(position)
                sectionHeaderViewHolder.bind(sectionHeader.title)
            }
            itemViewTypeItem -> {
                val sectionViewHolder = holder as SectionViewHolder
                val sectionItem = getItem(position)
                sectionViewHolder.bind(sectionItem,position)
            }
        }

    }

    private fun getSectionForPosition(position: Int): Section {
        var adjustedPosition = position
        for (section in list) {
            adjustedPosition -= if (adjustedPosition == 0) {
                return section
            } else if (adjustedPosition <= section.sections.size) {
                // Move to the next section
                section.sections.size + 1
            } else {
                // Move to the next section
                section.sections.size + 1
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
        return if (isSectionHeader(position)) itemViewTypeHeader else itemViewTypeItem
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
        private val txtLastName: TextView = view.findViewById(R.id.sectionDesignLastName)
        private val txtDate: TextView = view.findViewById(R.id.sectionDesignDate)
        private val edit: ImageView = view.findViewById(R.id.edit)
        private val delete: ImageView = view.findViewById(R.id.delete)

        fun bind(sectionModel: SectionModel, position: Int){
            txtName.text = sectionModel.name
            txtDate.text = sectionModel.date
            txtLastName.text = sectionModel.lastName

            edit.setOnClickListener(){
                onItemClickListener.onEditClicked(sectionModel, position)
            }

            delete.setOnClickListener(){
                onItemClickListener.onDeleteClicked(sectionModel)
            }

        }
    }

    inner class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val txtHeader: TextView = itemView.findViewById(R.id.sectionHeader)
        fun bind(header: String){
            txtHeader.text = header
        }
    }

}