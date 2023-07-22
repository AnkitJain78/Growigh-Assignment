package com.example.assignment.presentation.feeds

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentHomeBinding
import com.example.assignment.domain.model.Feed
import com.example.assignment.presentation.upload.UploadActivity
import com.example.assignment.utils.SwipeToShareCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: FeedAdapter
    private var feedList = ArrayList<Feed>()
    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val btmNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        btmNav.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.feedRecycler.layoutManager =
            LinearLayoutManager(requireActivity().baseContext, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        adapter = FeedAdapter(feedList, requireContext(), viewModel)
        binding.feedRecycler.adapter = adapter
        viewModel.feed.observe(viewLifecycleOwner) {
            adapter.clear()
            adapter.updateFeed(it)
            binding.swipeContainer.isRefreshing = false
        }
        viewModel.isErrorOccur.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireActivity().baseContext,
                    "Error in loading API Request",
                    Toast.LENGTH_SHORT
                ).show()
                binding.swipeContainer.isRefreshing = false
            }
        }
        viewModel.getFeeds(1, false)
        binding.fabButton.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }
        setUpSwipeRefresh()
        setUpRightSwipeShare()
        showHideFAB()
    }

    private fun showHideFAB() {
        binding.feedRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) binding.fabButton.hide();
                else binding.fabButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        })
    }

    private fun setUpSwipeRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getFeeds((2..5).random(), true)
        }
        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun setUpRightSwipeShare() {
        val swipeToShareCallback =
            object : SwipeToShareCallback(requireContext(), 0, ItemTouchHelper.RIGHT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition;
                    val feed = adapter.getFeedAtPosition(position);
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = feed.urls.regular
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    adapter.notifyItemChanged(position)
                    startActivity(Intent.createChooser(sharingIntent, "Share via"))
                }
            }
        swipeToShareCallback.rightBG = ContextCompat.getColor(requireContext(), R.color.swipeShare)
        swipeToShareCallback.rightLabel = ""
        swipeToShareCallback.rightIcon =
            AppCompatResources.getDrawable(requireContext(), R.drawable.icon_share)
        val itemTouchHelper = ItemTouchHelper(swipeToShareCallback)
        itemTouchHelper.attachToRecyclerView(binding.feedRecycler)
    }

}