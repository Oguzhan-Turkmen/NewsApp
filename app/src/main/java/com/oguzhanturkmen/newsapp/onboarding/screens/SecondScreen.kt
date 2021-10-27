package com.oguzhanturkmen.newsapp.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.databinding.FragmentSecondScreenBinding


class SecondScreen : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.finish.setOnClickListener() {
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
            onBoardingFinished()
        }
        return view
    }
    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}