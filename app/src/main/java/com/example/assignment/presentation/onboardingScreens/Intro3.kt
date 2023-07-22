package com.example.assignment.presentation.onboardingScreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment.databinding.FragmentIntro3Binding
import com.example.assignment.presentation.MainActivity

class Intro3 : Fragment() {
    private lateinit var binding: FragmentIntro3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentIntro3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.setOnClickListener {
            onBoardingFinished()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.btnSkip.setOnClickListener {
            onBoardingFinished()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun onBoardingFinished() {
        val sharedPreferences = activity?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("isFinished", true)
        editor?.apply()
    }
}