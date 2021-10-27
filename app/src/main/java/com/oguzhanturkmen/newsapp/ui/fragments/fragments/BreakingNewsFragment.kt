package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.adapters.NewsAdapter
import com.oguzhanturkmen.newsapp.databinding.FragmentBreakingNewsBinding
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel
import com.oguzhanturkmen.newsapp.util.Constans.Companion.Query_Page_Size
import com.oguzhanturkmen.newsapp.util.Resourcee
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var  viewModel: NewsViewModel
    lateinit var  newsAdapter: NewsAdapter
    val TAG = "BreakingNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecylerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment,bundle)
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resourcee.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Query_Page_Size + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages

                    }
                }
                is Resourcee.Error ->{
                    hideProgressBar()
                    response.message?.let {message->
                        Log.e(TAG,"Error: $message")
                    }
                }
                is Resourcee.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    private fun  showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = false
    }

    private fun  hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListiner  = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeggining = firstVisibleItemPosition >=0
            val isTotalMoreThanVisible = totalItemCount >= Query_Page_Size
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeggining && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getBreakingNews("tr")
                isScrolling = false
            }

        }
    }

    private  fun  setupRecylerView(){
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListiner)

        }
        }
    }
