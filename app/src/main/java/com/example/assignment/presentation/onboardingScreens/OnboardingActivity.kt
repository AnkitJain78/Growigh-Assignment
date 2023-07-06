package com.example.assignment.presentation.onboardingScreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.assignment.R
import com.example.assignment.databinding.ActivityOnboardingBinding
import com.example.assignment.presentation.MainActivity
import com.example.assignment.utils.ZoomOutPageTransformer
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

private const val NUM_PAGES = 3

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (onBoardingFinished()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val fragmentList = arrayListOf(
            Intro1(),
            Intro2(),
            Intro3()
        )
        val adapter = OnBoardingAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )
        binding.onBoardingViewPager.adapter = adapter
        binding.onBoardingViewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPreferences = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFinished", false)
    }

    override fun onBackPressed() {
        if (binding.onBoardingViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.onBoardingViewPager.setCurrentItem(binding.onBoardingViewPager.currentItem - 1)
        }
    }
}