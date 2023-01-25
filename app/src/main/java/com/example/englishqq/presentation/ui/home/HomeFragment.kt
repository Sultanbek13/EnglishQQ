package com.example.englishqq.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentHomeBinding
import com.example.englishqq.presentation.adapter.HomeAdapter
import com.example.englishqq.presentation.ui.home.impl.HomeViewModelImpl
import com.example.englishqq.presentation.ui.main.MainFragmentDirections
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Pulse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModelImpl by viewModel()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { HomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHome.adapter = adapter
        binding.lottieAnimMainWithTaskList.playAnimation()

        adapter.setOnClickListener { typeId, themeName ->
            try {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToCheckListDialog(typeId, themeName))
            } catch (e: Exception) {
                toast(requireContext(), e.message!!)
            }
        }

        setObservers()
        viewModel.getUserName()
        viewModel.getListCategory()

    }

    private fun setObservers() {
        viewModel.allCategoryFlow.onEach {
            when (it.status) {
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message!!)
                }
                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    adapter.submitList(it.data)
                }
                ResourceState.LOADING -> {
                    loading(binding.loading, true)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getUserNameFlow.onEach {
            binding.tvName.text = "Hello, ${it.toString()}"
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}