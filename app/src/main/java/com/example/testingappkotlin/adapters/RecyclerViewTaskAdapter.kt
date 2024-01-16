package com.example.testingappkotlin.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.modals.RecyclerViewTaskModal
import com.example.testingappkotlin.R
import java.util.Locale

class RecyclerViewTaskAdapter(val context: Context, private var list: List<RecyclerViewTaskModal>) : RecyclerView.Adapter<RecyclerViewTaskAdapter.SingleImageViewHolder>(){

    fun setFilteredList(modelList: List<RecyclerViewTaskModal>){
        this.list = modelList
        notifyDataSetChanged()
    }

    inner class SingleImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val name: TextView = itemView.findViewById(R.id.singleImageName)
        private val textView: TextView = itemView.findViewById(R.id.singleImageText)
        private val message: TextView = itemView.findViewById(R.id.singleImageMessage)
        private val time: TextView = itemView.findViewById(R.id.singleImageTime)
        private val statusImage: ImageView = itemView.findViewById(R.id.singleImageStatus)
        private val image: ImageView = itemView.findViewById(R.id.singleImage)
        private val tripleImage1: ImageView = itemView.findViewById(R.id.tripleImage1)
        private val tripleImage2: ImageView = itemView.findViewById(R.id.tripleImage2)
        private val tripleImage3: ImageView = itemView.findViewById(R.id.tripleImage3)
        private val tripleImage4: ImageView = itemView.findViewById(R.id.tripleImage4)
        private val tripleImage5: ImageView = itemView.findViewById(R.id.tripleImage5)

        fun bind(model: RecyclerViewTaskModal) {


            if (!model.image.isNullOrEmpty()) {

                // Update visibility and image resource based on the number of images
                when (model.image.size) {
                    1 -> {
                        image.visibility = View.VISIBLE
                        image.setImageResource(model.image[0])

                        tripleImage1.visibility = View.GONE
                        tripleImage2.visibility = View.GONE
                        tripleImage3.visibility = View.GONE
                        tripleImage4.visibility = View.GONE
                        tripleImage5.visibility = View.GONE
                        textView.visibility = View.GONE

                    }2 -> {
                        tripleImage1.visibility = View.VISIBLE
                        tripleImage2.visibility = View.VISIBLE
                        tripleImage1.setImageResource(model.image[1])
                        tripleImage2.setImageResource(model.image[1])

                        tripleImage3.visibility = View.GONE
                        tripleImage4.visibility = View.GONE
                        tripleImage5.visibility = View.GONE
                        textView.visibility = View.GONE
                        image.visibility = View.GONE
                    }
                    3 -> {
                        tripleImage1.visibility = View.VISIBLE
                        tripleImage2.visibility = View.VISIBLE
                        tripleImage3.visibility = View.VISIBLE
                        tripleImage1.setImageResource(model.image[2])
                        tripleImage2.setImageResource(model.image[2])
                        tripleImage3.setImageResource(model.image[2])

                        tripleImage4.visibility = View.GONE
                        tripleImage5.visibility = View.GONE
                        image.visibility = View.GONE
                        textView.visibility = View.GONE

                    }
                    4 -> {
                        tripleImage1.visibility = View.VISIBLE
                        tripleImage2.visibility = View.VISIBLE
                        tripleImage3.visibility = View.VISIBLE
                        tripleImage4.visibility = View.VISIBLE
                        tripleImage1.setImageResource(model.image[3])
                        tripleImage2.setImageResource(model.image[3])
                        tripleImage3.setImageResource(model.image[3])
                        tripleImage4.setImageResource(model.image[3])

                        tripleImage5.visibility = View.GONE
                        image.visibility = View.GONE
                        textView.visibility = View.GONE
                    }
                    else -> {
                        tripleImage1.visibility = View.VISIBLE
                        tripleImage2.visibility = View.VISIBLE
                        tripleImage3.visibility = View.VISIBLE
                        tripleImage4.visibility = View.VISIBLE
                        tripleImage5.visibility = View.VISIBLE
                        tripleImage1.setImageResource(model.image[3])
                        tripleImage2.setImageResource(model.image[3])
                        tripleImage3.setImageResource(model.image[3])
                        tripleImage4.setImageResource(model.image[3])
                        textView.visibility = View.GONE
                        image.visibility = View.GONE
                    }
                }
            } else {
                val initials = model.name.take(2).uppercase(Locale.getDefault())
                textView.text = initials
                textView.visibility = View.VISIBLE
                tripleImage1.visibility = View.GONE
                tripleImage2.visibility = View.GONE
                tripleImage3.visibility = View.GONE
                tripleImage4.visibility = View.GONE
                tripleImage5.visibility = View.GONE
                image.visibility = View.GONE
            }

            //If you have only one image

//            if (model.image != null) {
//                image.setImageResource(model.image[0])
//                image.visibility = View.VISIBLE
//            }
//            else {
//
//                // Display first two letters of the name as an image placeholder
//
//                val initials = model.name.take(2).toUpperCase(Locale.getDefault())
//                textView.text = initials
//                textView.visibility = View.VISIBLE
//            }

            //Set other data
            name.text = model.name
            message.text = model.message
            time.text = model.time

            //Status color

            val color = if (model.status) R.color.green else R.color.red
            statusImage.setColorFilter(itemView.context.getColor(color), PorterDuff.Mode.SRC_IN)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_image_design,parent,false)
        return SingleImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SingleImageViewHolder, position: Int) {
        val recyclerViewTaskModal = list[position]
        holder.bind(recyclerViewTaskModal)
    }

}