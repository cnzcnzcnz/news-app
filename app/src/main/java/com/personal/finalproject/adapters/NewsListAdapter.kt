package com.personal.finalproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.personal.finalproject.databinding.ItemNewsBinding
import com.personal.finalproject.models.Articles

class NewsListAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Articles, NewsListAdapter.NewsListHolder>(DiffCallback) {

    class NewsListHolder(private var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Articles) {
            binding.data = news
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        return NewsListHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        val news = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(news, position)
        }
        holder.bind(news)
    }

    class OnClickListener(val listener: (article: Articles, position: Int) -> Unit) {
        fun onClick(article: Articles, position: Int) = listener(article, position)
    }

    override fun submitList(list: List<Articles>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem === newItem
        }
    }
}