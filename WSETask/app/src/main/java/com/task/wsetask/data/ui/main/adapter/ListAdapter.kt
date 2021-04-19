package com.task.wsetask.data.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.task.wsetask.R
import com.task.wsetask.data.model.Content


class ListAdapter(private val contents: ArrayList<Content>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val ITEM = 1
    private var isLoadingAdded = false

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBookName: AppCompatTextView = itemView.findViewById(R.id.tvBookName)
        fun bind(content: Content) {
            itemView.apply {
                tvBookName.text = content.title
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById<View>(R.id.loadmore_progress) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.item_layout, parent, false)
                viewHolder = DataViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM -> {
                val dataViewHolder = holder as DataViewHolder
                dataViewHolder.bind(contents[position])
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == contents.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Content("", "", "", "", "", "", "", ""))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = contents.size - 1
        contents.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun add(content: Content) {
        contents.add(content)
        notifyItemInserted(contents.size - 1)
    }

    fun addAll(contentResults: List<Content?>) {
        for (result in contentResults) {
            add(result!!)
        }
    }

//    private fun getItem(position: Int): Content {
//        return contents[position]
//    }


    fun addContents(contents: List<Content>) {
        this.contents.apply {
            clear()
            addAll(contents)
        }

    }
}