package com.example.englishqq.presentation.ui.test

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
import com.example.englishqq.databinding.FragmentTestBinding
import com.example.englishqq.presentation.adapter.TestAdapter
import com.example.englishqq.presentation.ui.test.impl.TestViewModelImpl
import com.example.englishqq.utils.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment(R.layout.fragment_test) {

    private val binding: FragmentTestBinding by viewBinding()
    private val viewModel: TestViewModelImpl by viewModel()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { TestAdapter() }
    private val args: TestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerTest.adapter = adapter

        binding.btnNext.setOnClickListener {
            findNavController().navigate(TestFragmentDirections.actionTestFragmentToFinishFragment(args.testId))
        }

        binding.iconClose.setOnClickListener {
            findNavController().popBackStack()
        }

        setObservers()
        setBtnDoneAndAnim()

        viewModel.getListTestContent(args.testId)

    }

    private fun setObservers() {
        viewModel.allTestFlow.onEach {
            when (it.status) {
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }

                ResourceState.LOADING -> loading(binding.loading, true)

                ResourceState.SUCCESS -> {
                    binding.apply {
                        loading(binding.loading, false)
                        if(it.data != null) {
                            adapter.models = it.data.toMutableList()
                            setDots(dotsIndicator, viewPagerTest)
                        }
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.loadingTest.observe(viewLifecycleOwner) {
            loading(binding.loading, it)
        }
    }

    private fun setBtnDoneAndAnim() {
        binding.apply {
            viewPagerTest.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    btnNext.isVisible = position + 1 == adapter.itemCount
                }
            })
        }
    }
}