package com.example.englishqq.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.englishqq.R
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.rvHome.adapter = adapter

        adapter.onItemClick {
            try {
                navController.navigate(R.id.action_homeFragment_to_checkListDialog)
            } catch (e: Exception) {
            }
        }

        viewModel.getThemeInfo()

        setObservers()

    }

    private fun setObservers() {
        viewModel.themeInfo.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    adapter.models = it.data!!.toMutableList()
                }
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
            }
        }
    }
}