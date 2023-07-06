package com.example.assignment.presentation.onboardingScreens

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(list: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    private val frList = list

    override fun getItemCount(): Int {
        return frList.size
    }

    override fun createFragment(position: Int): Fragment {
        return frList[position]
    }
}