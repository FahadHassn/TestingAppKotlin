package com.example.testingappkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.R
import com.example.testingappkotlin.models.VideoModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class VideoViewpagerAdapter(options: FirebaseRecyclerOptions<VideoModel>) :
    FirebaseRecyclerAdapter<VideoModel, VideoViewpagerAdapter.ViewPagerViewHolder>(options) {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoView: VideoView = itemView.findViewById(R.id.recycler_design_video_view)
        private val videoHeader: TextView = itemView.findViewById(R.id.recycler_design_video_header)
        private val videoDescription: TextView = itemView.findViewById(R.id.recycler_design_video_description)
        private val process: ProgressBar = itemView.findViewById(R.id.recycler_design_video_progress)

        fun setData(videoModel: VideoModel) {
            videoView.setVideoPath(videoModel.video)
            videoHeader.text = videoModel.videoHeader
            videoDescription.text = videoModel.videoDescription

            videoView.setOnPreparedListener { mediaPlayer ->
                process.visibility = View.GONE
                mediaPlayer.start() }

            videoView.setOnCompletionListener { mediaPlayer ->
                mediaPlayer.start() }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_recyclerview_design,parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int, model: VideoModel) {
        holder.setData(model)
    }
}