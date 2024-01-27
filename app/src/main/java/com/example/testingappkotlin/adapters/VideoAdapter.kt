package com.example.testingappkotlin.adapters

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingappkotlin.R
import com.example.testingappkotlin.models.VideoModel

class VideoAdapter(val context: Context, private val videoModelList: List<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var currentlyPlayingVideoView: VideoView? = null

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val videoView: VideoView = itemView.findViewById(R.id.recycler_design_video_view)
        //val playPauseButton: ImageView = itemView.findViewById(R.id.recycler_design_play_pause_button)

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

        holder.videoView.setVideoURI(Uri.parse(videoModel.video))

        // Set MediaController to null to hide the default controller
        holder.videoView.setMediaController(null)

        // Set a click listener to toggle play/pause
//        holder.playPauseButton.setOnClickListener {
//            if (holder.videoView.isPlaying) {
//                holder.videoView.pause()
//                holder.playPauseButton.setImageResource(R.drawable.baseline_play_circle_24)
//            } else {
//                startVideo(holder)
//            }
//        }
//
//        // Set a click listener on the VideoView to toggle play/pause when clicked
//        holder.videoView.setOnClickListener {
//            if (holder.videoView.isPlaying) {
//                holder.videoView.pause()
//                holder.playPauseButton.setImageResource(R.drawable.baseline_play_circle_24)
//            } else {
//                startVideo(holder)
//            }
//        }
//
//        holder.videoView.setOnCompletionListener {
//            holder.playPauseButton.setImageResource(R.drawable.baseline_play_circle_24)
//        }
//    }
//
//    private fun startVideo(holder: VideoViewHolder) {
//        // If there is a currently playing video, pause it
//        currentlyPlayingVideoView?.let {
//            it.pause()
//            holder.playPauseButton.setImageResource(R.drawable.baseline_play_circle_24)
//        }
//        // Start the new video
//        holder.videoView.start()
//        holder.playPauseButton.setImageResource(R.drawable.baseline_pause_circle_24)
//
//        // Update the currentlyPlayingVideoView
//        currentlyPlayingVideoView = holder.videoView
    }
}