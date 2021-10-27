package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.databinding.FragmentArticleDetailBinding
import com.oguzhanturkmen.newsapp.databinding.FragmentBreakingNewsBinding
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var  viewModel: NewsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        }

}