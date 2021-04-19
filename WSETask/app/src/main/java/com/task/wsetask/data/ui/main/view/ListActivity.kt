package com.task.wsetask.data.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.wsetask.R
import com.task.wsetask.data.api.ApiHelper
import com.task.wsetask.data.api.RetrofitBuilder
import com.task.wsetask.data.model.Article
import com.task.wsetask.data.model.Content
import com.task.wsetask.data.ui.base.ViewModelFactory
import com.task.wsetask.data.ui.main.MainViewModel
import com.task.wsetask.data.ui.main.adapter.ListAdapter
import com.task.wsetask.data.ui.main.adapter.PaginationScrollListener
import com.task.wsetask.utils.Constants
import com.task.wsetask.utils.Status


class ListActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        setupViewModel()
        setupUI()
        val author = intent.getStringExtra(Constants.SEARCH_KEY)
        author?.let {
            setupObservers(author, currentPage)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListAdapter(arrayListOf())
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView.adapter = adapter
        val manager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : PaginationScrollListener(manager){
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
            }

            override var isLastPage: Boolean = false
            override var isLoading: Boolean = false

        })
    }

    private fun setupObservers(author: String, pageNum : Int) {
        viewModel.getArticles(author, pageNum).observe(this, { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let {
                            if (pageNum == 1) {
                                retrieveList(it)
                            } else {
                                retrieveNextPage(it)
                            }
                        }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(article: Article) {
        adapter.apply {
            val contents : ArrayList<Content> = article.contents
            if (contents.size > 0) {
                verifyContent(contents = contents)
                addContents(contents)
                if (currentPage < article.totalPage) adapter.addLoadingFooter() else isLastPage = true
                notifyDataSetChanged()
            } else
                Toast.makeText(applicationContext, "No Content available according your search", Toast.LENGTH_LONG).show()
        }
    }

    private fun retrieveNextPage(article: Article) {
        adapter.apply {
            removeLoadingFooter()
            isLoading = false
            val contents : ArrayList<Content> = article.contents
            verifyContent(contents = contents)
            addAll(contents)
            if (currentPage != article.totalPage) addLoadingFooter() else isLastPage = true
        }
    }

    private fun verifyContent(contents : ArrayList<Content>) {
        for (content : Content in contents) {
            if (content.title.isEmpty()) {
                if (content.link.isEmpty()) {
                    contents.remove(content)
                } else {
                    content.title = content.link
                }
            }
        }
        val titleComparator = TitleComparator()
        contents.sortWith(titleComparator)
    }

    class TitleComparator: Comparator<Content>{
        override fun compare(o1: Content?, o2: Content?): Int {
            if(o1 == null || o2 == null){
                return 0
            }
            return o1.title.compareTo(o2.title)
        }
    }
}