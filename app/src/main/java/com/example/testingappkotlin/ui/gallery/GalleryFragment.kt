package com.example.testingappkotlin.ui.gallery

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testingappkotlin.databinding.FragmentGalleryBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private var pathUri: Uri? = null
    private var imagePickerCode = 12
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userId: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[GalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        firebaseAuth = Firebase.auth
        databaseReference = Firebase.database.reference.child("Users")
        storageReference = Firebase.storage.reference.child("Users Images")
        userId = firebaseAuth.currentUser?.uid.toString()

        binding.apply {

            //select image from galley
            galleryImageView.setOnClickListener {
                showImage()
            }

            //upload image in database
            galleyButton.setOnClickListener {
                binding.galleyProgress.visibility = View.VISIBLE
                uploadImage(userId)
            }

        }

        return root
    }

    private fun uploadImage(id: String) {
        val hashMap = HashMap<String, Any>()

        if (pathUri != null){
            val sr = storageReference.child(pathUri?.lastPathSegment!!)
            sr.putFile(pathUri!!).addOnSuccessListener {
                sr.downloadUrl.addOnSuccessListener { uri ->
                    hashMap["image"] = uri.toString()
                    databaseReference.child(id).updateChildren(hashMap).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Update Successfully", Toast.LENGTH_SHORT)
                            .show()
                        binding.galleyProgress.visibility = View.GONE
                    }.addOnFailureListener {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.galleyProgress.visibility = View.GONE
                    }
                }
            }
        }else{
            Toast.makeText(
                requireContext(),
                "Select image",
                Toast.LENGTH_SHORT
            ).show()
            binding.galleyProgress.visibility = View.GONE
        }

    }

    private fun showImage() {

        Dexter.withContext(context)
            .withPermission(checkVersion())
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setType("image/*")
                    startActivityForResult(
                        Intent.createChooser(intent, "Choose Image"),
                        imagePickerCode
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
        if (requestCode == imagePickerCode && resultCode == RESULT_OK){
            if (data?.data != null) {
                pathUri = data.data;
//                Picasso.get().load(pathUri).into(binding.galleryImageView)
                Glide.with(requireContext()).load(pathUri).into(binding.galleryImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}