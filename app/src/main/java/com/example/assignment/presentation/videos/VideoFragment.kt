package com.example.assignment.presentation.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.databinding.FragmentVideoBinding
import com.example.assignment.domain.model.Video
import com.google.android.material.bottomnavigation.BottomNavigationView

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private var videoList = listOf<Video>(
        Video(
            "Animals have come to mean so much in our lives.",
            "Smith",
            "https://www.exit109.com/~dnn/clips/RW20seconds_1.mp4"
        ),
        Video(
            "Health is Wealth !",
            "Chetan Vlogs",
            "https://vod-progressive.akamaized.net/exp=1690015435~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F645%2F14%2F353226442%2F1434907996.mp4~hmac=41e71065563feccc7332b587e2f0273f95ceb6f4ccb0c0eb36e05cdc6ab584c7/vimeo-prod-skyfire-std-us/01/645/14/353226442/1434907996.mp4"
        ),
        Video(
            "Just Riding !",
            "Vijay123",
            "https://www.exit109.com/~dnn/clips/RW20seconds_2.mp4"
        ),
        Video(
            "It's just GENIUS \uD83E\uDD2F#camping #survival #bushcraft #outdoors",
            "Cool Guy",
            "https://vod-progressive.akamaized.net/exp=1690018278~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4046%2F16%2F420234573%2F1814650134.mp4~hmac=9c2932c3a024e31d92e7f7a4189d68db4e143cc6d707044b8386b55dc55552c0/vimeo-prod-skyfire-std-us/01/4046/16/420234573/1814650134.mp4"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.VideoViewPager.adapter = VideoAdapter(videoList, requireContext());
        val btmNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        btmNav.visibility = View.GONE
        binding.btnBack.setOnClickListener {
            btmNav.selectedItemId = R.id.home
        }
    }


}