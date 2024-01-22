package com.example.testingappkotlin.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testingappkotlin.activities.SharedPreferencesLoginActivity
import com.example.testingappkotlin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        val name = sharedPref?.getString("name",null) ?: "Someone"
        val email = sharedPref?.getString("email",null) ?: "someone@gmail.com"
        val password = sharedPref?.getString("password",null) ?: "......"

        binding.homeNameText.text = name
        binding.homeEmailText.text = email
        binding.homePasswordText.text = password

        binding.button5.setOnClickListener{
            editor?.putBoolean("state", false)
            editor?.apply()
            startActivity(Intent(context, SharedPreferencesLoginActivity::class.java))
            requireActivity().finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}