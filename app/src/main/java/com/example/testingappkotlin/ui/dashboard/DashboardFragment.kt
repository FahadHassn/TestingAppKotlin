package com.example.testingappkotlin.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var navController: NavController
    private var videoPickerCode = 12

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

        _binding!!.buttonDashboard.setOnClickListener{
            navController.navigate(R.id.action_navigation_dashboard_to_recyclerViewSectionTaskActivity)
        }

        binding.buttonSelectVideo.setOnClickListener {
            showVideo()
        }

        return root
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
                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(binding.videoView)
                binding.apply {
                    videoView.setVideoURI(data.data)
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