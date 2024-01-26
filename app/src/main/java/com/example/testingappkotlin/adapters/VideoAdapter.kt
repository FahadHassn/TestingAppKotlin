package com.example.testingappkotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.R
import com.example.testingappkotlin.models.VideoModel

class VideoAdapter(val context: Context, private val videoModelList: List<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val videoView: VideoView = itemView.findViewById(R.id.recycler_design_video_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_recyclerview_design,parent,false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoModelList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoModel = videoModelList[position]

        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.videoView)
        holder.videoView.setVideoPath(videoModel.video)
        holder.videoView.setMediaController(mediaController)

        // Start playing the video
        holder.videoView.start()

    }


}