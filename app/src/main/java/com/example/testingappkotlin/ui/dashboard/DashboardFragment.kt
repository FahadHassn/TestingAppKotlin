package com.example.testingappkotlin.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testingappkotlin.R
import com.example.testingappkotlin.databinding.FragmentDashboardBinding
import com.example.testingappkotlin.models.VideoModel
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var navController: NavController
    private var pathUri: Uri? = null
    private var videoPickerCode = 12
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        databaseReference = Firebase.database.reference.child("Videos")
        storageReference = Firebase.storage.reference.child("videos")

        _binding!!.buttonDashboard.setOnClickListener{
            navController.navigate(R.id.action_navigation_dashboard_to_recyclerViewSectionTaskActivity)
        }

        binding.buttonSelectVideo.setOnClickListener {
            showVideo()
        }

        binding.buttonUploadVideo.setOnClickListener {
            val header = binding.videoHeader.text.toString()
            val description = binding.videoDescription.text.toString()
            if (header.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    "Enter title",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }else if (description.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    "Enter description",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }else{
                binding.videoProgress.visibility = View.VISIBLE
                uploadVideo(header,description)
            }
        }

        return root
    }

    private fun uploadVideo(header: String, description: String) {
        val sr = pathUri?.lastPathSegment?.let { storageReference.child(it) }
        if (sr != null) {
            pathUri?.let {
                sr.putFile(it)
                    .addOnSuccessListener {
                        sr.downloadUrl.addOnSuccessListener { uri ->
                            val id: String? = databaseReference.push().key
                            val videoModel = VideoModel(id, header, description, uri.toString())
                            if (id != null) {
                                databaseReference.child(id).setValue(videoModel).addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Upload Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.videoProgress.visibility = View.GONE
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Something went wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.videoProgress.visibility = View.GONE
                                }
                            }
                        }
                    }
            }
        }else{
            Toast.makeText(
                requireContext(),
                "Select Video",
                Toast.LENGTH_SHORT
            ).show()
            binding.videoProgress.visibility = View.GONE
        }

    }

    private fun showVideo() {
        Dexter.withContext(context)
            .withPermission(checkVersion())
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("video/*")
                    startActivityForResult(
                        Intent.createChooser(intent, "Choose video"),
                        videoPickerCode
                    )
//                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                        startActivityForResult(intent, IMAGE_PICKER_CODE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: com.karumi.dexter.listener.PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    Toast.makeText(context,"Go to Setting and Allow Permissions", Toast.LENGTH_SHORT).show()
                }

            }).check()
    }

    private fun checkVersion(): String {
        return if (Build.VERSION.SDK_INT >= 33){
            Manifest.permission.READ_MEDIA_IMAGES
        }else{
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == videoPickerCode && resultCode == Activity.RESULT_OK){
            if (data?.data != null) {
                pathUri = data.data
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(binding.videoView)
                binding.apply {
                    videoView.setVideoURI(pathUri)
                    videoView.setMediaController(mediaController)
                    videoView.start()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}