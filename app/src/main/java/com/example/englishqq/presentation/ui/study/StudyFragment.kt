package com.example.englishqq.presentation.ui.study

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentStudyBinding
import com.example.englishqq.presentation.adapter.StudyAdapter
import com.example.englishqq.presentation.ui.study.impl.StudyViewModelImpl
import com.example.englishqq.utils.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : Fragment(R.layout.fragment_study) {

    private val binding: FragmentStudyBinding by viewBinding()
    private val viewModel: StudyViewModelImpl by viewModel()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { StudyAdapter(requireContext()) }
    private val args: StudyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerStudy.adapter = adapter

        setObservers()
        setBtnUnderstandablyAndAnim()

        binding.iconClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUnderstandably.setOnClickListener {
            findNavController().navigate(
                StudyFragmentDirections.actionStudyFragmentToTestFragment(
                    args.themeId
                )
            )
        }

        viewModel.getListContent(args.themeId)

    }

    private fun setObservers() {
        viewModel.allContentFlow.onEach {
            when (it.status) {
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
                ResourceState.SUCCESS -> {
                    binding.apply {
                        loading(loading, false)
                        if(it.data != null) {
                            adapter.models = it.data.toMutableList()
                            setDots(dotsIndicator, viewPagerStudy)
                        }
                    }
                }
                ResourceState.LOADING -> {
                    loading(binding.loading, true)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.loadingStudy.observe(viewLifecycleOwner) {
            loading(binding.loading, it)
        }
    }

    private fun setBtnUnderstandablyAndAnim() {
        binding.apply {
            viewPagerStudy.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position + 1 == adapter.itemCount) {
                        btnUnderstandably.isVisible = true
                        playConfettiAnim(binding.confettiView)
                    } else {
                        btnUnderstandably.isVisible = false
                    }
                }
            })
        }
    }
}