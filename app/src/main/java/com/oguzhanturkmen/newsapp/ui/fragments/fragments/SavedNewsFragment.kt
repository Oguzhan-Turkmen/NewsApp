package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.adapters.NewsAdapter
import com.oguzhanturkmen.newsapp.databinding.FragmentSavedNewsBinding
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel
import com.oguzhanturkmen.newsapp.util.Resourcee
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SavedNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecylerView()
        viewModel = (activity as MainActivity).viewModel
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,bundle)
        }
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {articles ->
            newsAdapter.differ.submitList(articles)
        })
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "DELETED", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }
    private  fun  setupRecylerView(){
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}

