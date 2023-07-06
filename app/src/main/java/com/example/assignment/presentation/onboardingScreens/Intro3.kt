package com.example.assignment.presentation.onboardingScreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.databinding.FragmentIntro3Binding
import com.example.assignment.presentation.MainActivity
import org.xmlpull.v1.XmlPullParser

class Intro3 : Fragment() {
    private var binding: FragmentIntro3Binding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_intro3, container, false)
        val parser: XmlPullParser = resources.getLayout(R.layout.fragment_intro3)
        try {
            parser.next()
            parser.nextTag()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val attr = Xml.asAttributeSet(parser)
        binding?.progress = activity?.let { ProgressBtn(it.baseContext, attr) }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnReady?.setOnClickListener {
            onBoardingFinished()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding?.btnSkip?.setOnClickListener {
            onBoardingFinished()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onBoardingFinished() {
        val sharedPreferences = activity?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("isFinished", true)
        editor?.apply()
    }
}