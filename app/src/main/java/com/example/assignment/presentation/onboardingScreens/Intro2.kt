package com.example.assignment.presentation.onboardingScreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.assignment.R
import com.example.assignment.databinding.FragmentIntro2Binding
import com.example.assignment.presentation.MainActivity

class Intro2 : Fragment() {
    private lateinit var binding: FragmentIntro2Binding
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentIntro2Binding.inflate(layoutInflater, container, false)
        viewPager = activity?.findViewById(R.id.on_boarding_view_pager)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextImgBtn.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                viewPager.currentItem = 2
            }, 200)
        }
        binding.btnSkip.setOnClickListener {
            val sharedPreferences =
                activity?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putBoolean("isFinished", true)
            editor?.apply()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}