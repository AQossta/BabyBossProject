package com.example.babybossandroidapp.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.babybossandroidapp.R
import com.example.babybossandroidapp.data.model.OnboardingInfoList
import com.example.babybossandroidapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private lateinit var viewPagerCallBack: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupClickListeners()
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter()
        adapter.submitList(OnboardingInfoList.onboardingModelList)
        binding.viewPager2OnboardingFragment.adapter = adapter

        viewPagerCallBack = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUI(position)
            }
        }

        binding.dotsIndicatorOnboardingFragment.setViewPager2(binding.viewPager2OnboardingFragment)
        binding.viewPager2OnboardingFragment.registerOnPageChangeCallback(viewPagerCallBack)


        updateUI(0)
    }

    private fun setupClickListeners() {
        binding.btnNextOnboardingFragment.setOnClickListener {
            navigateToNext()
        }
    }

    private fun navigateToNext() {
        val currentPosition = binding.viewPager2OnboardingFragment.currentItem
        val isLastPage = currentPosition == adapter.itemCount - 1

        if (isLastPage) {
            findNavController().navigate(R.id.action_onboardingFragment_to_registrationFragment)
        } else {
            binding.viewPager2OnboardingFragment.currentItem = currentPosition + 1
        }
    }

    private fun updateUI(currentPosition: Int) {
        val isLastPage = currentPosition == adapter.itemCount - 1

        binding.btnNextOnboardingFragment.text = if (isLastPage) {
            "НАЧАТЬ"
        } else {
            "ДАЛЕЕ"
        }

        if (isLastPage) {
            binding.btnNextOnboardingFragment.animate().scaleX(1.05f).scaleY(1.05f).setDuration(200).start()
        } else {
            binding.btnNextOnboardingFragment.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager2OnboardingFragment.unregisterOnPageChangeCallback(viewPagerCallBack)
    }
}