package com.example.testingappkotlin.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.testingappkotlin.Modals.ItemViewModal
import com.example.testingappkotlin.R

class ListViewAdapter(private val context: Context, private val list: List<ItemViewModal>) : BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): ItemViewModal {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myConvertView = convertView
        if (myConvertView == null){
            myConvertView = LayoutInflater.from(context).inflate(R.layout.list_view_design,parent,false)
        }

        val listItems = getItem(position)
        val title = myConvertView?.findViewById<TextView>(R.id.listviewTitle)
        val subtitle = myConvertView?.findViewById<TextView>(R.id.listviewSubTitle)
        title?.text = listItems.title
        subtitle?.text = listItems.subtitle

        return myConvertView!!

    }

}
