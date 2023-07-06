package com.example.assignment.presentation.feeds

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.ItemFeedBinding
import com.example.assignment.domain.model.Feed

class FeedAdapter(private val feedList: ArrayList<Feed>, val context: Context) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    inner class FeedViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        with(holder){
            with(feedList[position]){
                binding.feedSource.text = this.source.name
                binding.feedTitle.text = this.title
                binding.feedImage
                Glide.with(context).load(this.urlToImage).into(binding.feedImage)
            }
        }
    }
    override fun getItemCount(): Int {
        return feedList.size
    }

    fun clear() {
        val size = feedList.size
        feedList.clear()
        notifyItemRangeChanged(0,size - 1)
    }

    fun updateFeed(list: List<Feed>) {
        feedList.addAll(list)
        notifyDataSetChanged()
    }

}