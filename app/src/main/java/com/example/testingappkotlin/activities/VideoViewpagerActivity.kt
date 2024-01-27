package com.example.testingappkotlin.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.testingappkotlin.adapters.VideoViewpagerAdapter
import com.example.testingappkotlin.databinding.ActivityVideoViewpagerBinding
import com.example.testingappkotlin.models.VideoModel
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.FirebaseRecyclerOptions.Builder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VideoViewpagerActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var videoViewpagerAdapter: VideoViewpagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference= FirebaseDatabase.getInstance().reference.child("Videos")

        val options: FirebaseRecyclerOptions<VideoModel> = Builder<VideoModel>()
            .setQuery(databaseReference, VideoModel::class.java)
            .build()

        videoViewpagerAdapter = VideoViewpagerAdapter(options)
        binding.viewPager.adapter = videoViewpagerAdapter

    }

    override fun onStart() {
        super.onStart()
        videoViewpagerAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        videoViewpagerAdapter.stopListening()
    }

}