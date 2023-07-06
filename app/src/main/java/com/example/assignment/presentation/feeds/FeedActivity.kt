package com.example.assignment.presentation.feeds

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.databinding.ActivityFeedsBinding
import com.example.assignment.domain.model.Feed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedsBinding
    private lateinit var adapter: FeedAdapter
    private var feedList = ArrayList<Feed>()
    private val viewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.feedRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = FeedAdapter(feedList, this)
        binding.feedRecycler.adapter = adapter
        viewModel.feed.observe(this){
            adapter.clear()
            adapter.updateFeed(it)
            binding.swipeContainer.isRefreshing = false
        }
        viewModel.isErrorOccur.observe(this){
            if(it) {
                Toast.makeText(this, "Error in loading API Request", Toast.LENGTH_SHORT).show()
                binding.swipeContainer.isRefreshing = false
            }
        }
        viewModel.getFeeds(1)
        setUpSwipeRefresh()
    }

    private fun setUpSwipeRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getFeeds((2..5).random())
        }
        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}