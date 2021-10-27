package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.adapters.NewsAdapter
import com.oguzhanturkmen.newsapp.databinding.FragmentBreakingNewsBinding
import com.oguzhanturkmen.newsapp.databinding.FragmentSavedNewsBinding
import com.oguzhanturkmen.newsapp.databinding.FragmentSearchNewsBinding
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel
import com.oguzhanturkmen.newsapp.util.Constans
import com.oguzhanturkmen.newsapp.util.Constans.Companion.Search_News_Delay
import com.oguzhanturkmen.newsapp.util.Resourcee
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var  viewModel: NewsViewModel
    lateinit var  newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecylerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
        }

        var job: Job? = null
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job= MainScope().launch {
                delay(Search_News_Delay)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }


        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resourcee.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                        val totalPages = newsResponse.totalResults / Constans.Query_Page_Size + 2
                        isLastPage = viewModel.searchNewsPage == totalPages
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

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constans.Query_Page_Size
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.searchNews(etSearch.text.toString())
                isScrolling = false
            } else {
                rvSearchNews.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private  fun  setupRecylerView(){
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)

        }
    }

}