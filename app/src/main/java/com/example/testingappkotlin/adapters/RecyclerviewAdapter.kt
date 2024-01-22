package com.example.testingappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.models.ItemViewModel
import com.example.testingappkotlin.R

class RecyclerviewAdapter(val context: Context, private val list: List<ItemViewModel>): RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val title: TextView = view.findViewById(R.id.listviewTitle)
        val subtitle: TextView = view.findViewById(R.id.listviewSubTitle)
        val parentView: ConstraintLayout = view.findViewById(R.id._designItemParentView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_view_design,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModal = list[position]
        holder.title.text = itemViewModal.title
        holder.subtitle.text = itemViewModal.subtitle

        holder.parentView.setOnClickListener(){
            Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
        }
    }
}