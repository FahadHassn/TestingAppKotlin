package com.example.testingappkotlin.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testingappkotlin.databinding.FragmentSlideshowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[SlideshowViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        //get data from firebase database
        firebaseAuth = FirebaseAuth.getInstance()
        val id = firebaseAuth.currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val name = snapshot.child("name").value.toString()
                    val email = snapshot.child("email").value.toString()
                    val phone = snapshot.child("phone").value.toString()
                    val password = snapshot.child("password").value.toString()
                    binding.profileName.text = name
                    binding.profileEmail.text = email
                    binding.profilePhone.text = phone
                    binding.profilePassword.text = password
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}