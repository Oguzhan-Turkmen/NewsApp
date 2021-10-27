package com.oguzhanturkmen.newsapp.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.database.ArticleDatabase
import com.oguzhanturkmen.newsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  viewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsRepository = NewsRepository(ArticleDatabase.invoke(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        supportActionBar?.hide()
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        Handler().postDelayed({
            bottomNavigationView.visibility = View.VISIBLE
        }, 2000)
        bottomNavigationView.visibility = View.GONE



    }
}