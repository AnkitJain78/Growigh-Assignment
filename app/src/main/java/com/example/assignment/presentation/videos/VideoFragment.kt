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
            "https://vod-progressive.akamaized.net/exp=1689991867~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F655%2F16%2F403278689%2F1724442803.mp4~hmac=c3252cfdfea2f4445f9f45752f0a5ffc0c7c2debed0e1b0c2d7e326a9748d235/vimeo-prod-skyfire-std-us/01/655/16/403278689/1724442803.mp4"
        ),
        Video(
            "Just Riding !",
            "Vijay123",
            "https://vod-progressive.akamaized.net/exp=1689991961~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F360%2F18%2F451802820%2F1987788051.mp4~hmac=b70da1820fb5b68cf163d57c9a221278ca9b1ec2b4c7b8a0e142f230b52fbe26/vimeo-prod-skyfire-std-us/01/360/18/451802820/1987788051.mp4"
        ),
        Video(
            "Health is Wealth !",
            "Chetan Vlogs",
            "https://vod-progressive.akamaized.net/exp=1689991578~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F645%2F14%2F353226442%2F1434907996.mp4~hmac=1de008a16e39e615a66ba5df2723ad7da909a1794b69b4bc039f082173a34de6/vimeo-prod-skyfire-std-us/01/645/14/353226442/1434907996.mp4"
        ),
        Video(
            "It's just GENIUS \uD83E\uDD2F#camping #survival #bushcraft #outdoors",
            "Cool Guy",
            "https://vod-progressive.akamaized.net/exp=1689991630~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F514%2F16%2F402571180%2F1720566575.mp4~hmac=3f7aef511060793d6fb4e67bbb276f0149ad285e40e8c5583b9fa9d390303b92/vimeo-prod-skyfire-std-us/01/514/16/402571180/1720566575.mp4"
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