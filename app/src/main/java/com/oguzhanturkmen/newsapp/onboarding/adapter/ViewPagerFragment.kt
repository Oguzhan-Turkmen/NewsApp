package com.oguzhanturkmen.newsapp.onboarding.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oguzhanturkmen.newsapp.databinding.FragmentViewPagerBinding
import com.oguzhanturkmen.newsapp.onboarding.adapter.ViewPagerAdapter
import com.oguzhanturkmen.newsapp.onboarding.screens.FirstScreen
import com.oguzhanturkmen.newsapp.onboarding.screens.SecondScreen

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater,container,false)
        val view = binding.root


        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        return view
    }
}