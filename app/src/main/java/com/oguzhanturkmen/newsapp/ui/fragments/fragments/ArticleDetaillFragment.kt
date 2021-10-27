package com.oguzhanturkmen.newsapp.ui.fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.oguzhanturkmen.newsapp.R
import com.oguzhanturkmen.newsapp.models.Article
import com.oguzhanturkmen.newsapp.ui.fragments.MainActivity
import com.oguzhanturkmen.newsapp.ui.fragments.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article_detaill.*

class ArticleDetaillFragment : Fragment(R.layout.fragment_article_detaill) {

    private lateinit var  article: Article
    lateinit var  viewModel: NewsViewModel
    val argss: ArticleDetaillFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        article = argss.articleUrl
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }




    }

}