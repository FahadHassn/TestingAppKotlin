package com.example.testingappkotlin.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingappkotlin.adapters.RecyclerviewAdapter
import com.example.testingappkotlin.adapters.VideoAdapter
import com.example.testingappkotlin.databinding.FragmentNotificationsBinding
import com.example.testingappkotlin.models.SectionModel
import com.example.testingappkotlin.models.VideoModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private var list = mutableListOf<VideoModel>()
    private lateinit var videoAdapter: VideoAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        databaseReference = Firebase.database.reference.child("Videos")

        binding.apply {

            videoRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            videoAdapter = VideoAdapter(requireContext(),list)
            videoRecyclerview.adapter = videoAdapter

            databaseReference.addValueEventListener(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        list.clear()
                        for (dataSnapshot in snapshot.children) {
                            val videoModel = snapshot.getValue(VideoModel::class.java)
                            videoRecyclerProgress.visibility = View.GONE
                            videoModel?.let {
                                list.add(it)
                            }
                        }
                        videoAdapter.notifyDataSetChanged()

                    } else {
                        videoRecyclerProgress.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Data not available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}