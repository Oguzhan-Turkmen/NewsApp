package com.oguzhanturkmen.newsapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.databinding.FragmentFirstScreenBinding
import com.oguzhanturkmen.newsapp.databinding.FragmentViewPagerBinding


class FirstScreen : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener() {
            viewPager?.currentItem = 1
        }
        return view
    }
}
