package com.example.testingappkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.R
import com.example.testingappkotlin.models.VideoModel

class VideoAdapter(val context: Context, private val videoModelList: List<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var currentPlayingPosition: Int? = null
    private var currentVideoView: VideoView? = null

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val videoView: VideoView = itemView.findViewById(R.id.recycler_design_video_view)
        val progressBar: ProgressBar = itemView.findViewById(R.id.recycler_design_video_progress)
        val videoHeader: TextView = itemView.findViewById(R.id.recycler_design_video_header)
        val videoDescription: TextView = itemView.findViewById(R.id.recycler_design_video_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_recyclerview_design,parent,false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoModelList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, @SuppressLint("RecyclerView") position: Int) {
//        val videoModel = videoModelList[position]
//
//        holder.apply {
//            val mediaController = MediaController(context)
//            mediaController.setAnchorView(videoView)
//            videoView.setVideoPath(videoModel.video)
//            videoView.setMediaController(mediaController)
//
//            videoHeader.text = videoModel.videoHeader
//            videoDescription.text = videoModel.videoDescription
//
//            videoView.setOnPreparedListener {
//                progressBar.visibility = View.GONE
//                it.start()
//            }
//        }

        val videoModel = videoModelList[position]

        holder.apply {
            val mediaController = MediaController(context)
            mediaController.setAnchorView(videoView)
            videoView.setVideoPath(videoModel.video)
            videoView.setMediaController(mediaController)

            videoHeader.text = videoModel.videoHeader
            videoDescription.text = videoModel.videoDescription

            videoView.setOnPreparedListener {
                progressBar.visibility = View.GONE

                // Check if there is a video currently playing
                if (currentPlayingPosition != null && currentPlayingPosition != position) {
                    // Pause the currently playing video
                    currentVideoView?.pause()
                }

                // Set the current video as the one being played
                currentPlayingPosition = position
                currentVideoView = videoView

                // Start playing the video
                it.start()
            }

            // Add an optional setOnCompletionListener to handle video completion
            videoView.setOnCompletionListener {
                // Reset the current playing position
                currentPlayingPosition = null
                currentVideoView = null
            }
        }

    }
}