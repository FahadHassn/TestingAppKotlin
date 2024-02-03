package com.example.testingappkotlin.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testingappkotlin.activities.FirebaseLoginActivity
import com.example.testingappkotlin.databinding.FragmentSlideshowBinding
import com.example.testingappkotlin.models.UserLoginModel
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var userLoginModel: UserLoginModel

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
        firebaseAuth = Firebase.auth
        databaseReference = Firebase.database.reference.child("Users")
        val id = firebaseAuth.currentUser?.uid
        if (id != null ){
            databaseReference.child(id).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){

                        //read data with keys
//                    val name = snapshot.child("name").value.toString()
//                    val email = snapshot.child("email").value.toString()
//                    val phone = snapshot.child("phone").value.toString()
//                    val image = snapshot.child("image").value.toString()

                        //read data with model
                        userLoginModel = snapshot.getValue(UserLoginModel::class.java)!!

                        val name = userLoginModel.name
                        val email = userLoginModel.email
                        val phone = userLoginModel.phone
                        val image = userLoginModel.image

                        if (_binding != null){
                            //set data
                            binding.profileName.setText(name)
                            binding.profileEmail.setText(email)
                            binding.profilePhone.setText(phone)
                            Glide.with(this@SlideshowFragment).load(image).into(binding.profileImageView)
                            binding.profileProgress.visibility = View.GONE
                            binding.profileUpdateButton.isEnabled = true
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        binding.apply {

            //update data
            profileUpdateButton.setOnClickListener {
                val newName = profileName.text.toString()
                val newEmail = profileEmail.text.toString()
                val newPhone = profilePhone.text.toString()

                if (newName.isEmpty() || newEmail.isEmpty() ||newPhone.isEmpty()) {
                    Toast.makeText(requireContext(),
                        "Fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }else {
                    binding.profileProgress.visibility = View.VISIBLE
                    updateData(newName,newEmail,newPhone)
                }
            }

            //delete account
            profileDeleteAccount.setOnClickListener {
                val user: FirebaseUser? = firebaseAuth.currentUser
                user?.delete()?.addOnSuccessListener {
                    databaseReference.removeValue()
                    firebaseAuth.signOut()
                    startActivity(Intent(requireContext(),FirebaseLoginActivity::class.java))
                    requireActivity().finish()
                    Toast.makeText(
                        requireContext(),
                        "Delete Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }?.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //logout
            profileLogout.setOnClickListener {
                firebaseAuth.signOut()
                startActivity(Intent(requireContext(),FirebaseLoginActivity::class.java))
                requireActivity().finish()
            }

        }

        return root
    }

    private fun updateData(newName: String, newEmail: String, newPhone: String) {

        val user = Firebase.auth.currentUser

        // Re-authenticate user
        val credential = EmailAuthProvider.getCredential(userLoginModel.email, userLoginModel.password)
        user?.reauthenticate(credential)?.addOnCompleteListener { reAuthTask ->
            if (reAuthTask.isSuccessful) {
                // Re-authentication successful, now update email
                user.updateEmail(newEmail).addOnCompleteListener { updateEmailTask ->
                    if (updateEmailTask.isSuccessful) {
                        // Update the corresponding data in the Realtime Database
                        val hashMap = hashMapOf<String, Any>(
                            "name" to newName,
                            "email" to newEmail,
                            "phone" to newPhone
                        )

                        databaseReference.updateChildren(hashMap)
                            .addOnSuccessListener {
                                binding.profileProgress.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    "Update Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                binding.profileProgress.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    "Update failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        // Handle the case where updating email fails
                        binding.profileProgress.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Failed to update email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    Log.e("TAG", "Email update failed", exception)
                    // Display a more specific error message based on the exception
                    Toast.makeText(requireContext(), "Failed to update email: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle the case where re-authentication fails
                binding.profileProgress.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Re-authentication failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}