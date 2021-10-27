package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.adapters.NewsAdapter
import com.oguzhanturkmen.newsapp.databinding.FragmentArticleBinding
import com.oguzhanturkmen.newsapp.databinding.FragmentBreakingNewsBinding
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article_detail.*
import kotlinx.android.synthetic.main.fragment_first_screen.*
import kotlinx.android.synthetic.main.item_article.view.*


class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.tvDescription.text = article.description
        Picasso.get().load(article.urlToImage).into(binding.ivArticleImage)
        binding.tvPublishedAt.text = article.publishedAt
        binding.tvTitle.text = article.title
        binding.tvSource.text = article.source?.name
        binding.getSource.setOnClickListener{
            article?.let {
                val directions =ArticleFragmentDirections.actionArticleFragmentToArticleDetaillFragment(it)
                view.findNavController().navigate(directions)
            }
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved", Snackbar.LENGTH_SHORT).show()
        }
        return  view

    }

}