package com.example.englishqq.ui.study

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.englishqq.R
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.FragmentStudyBinding
import com.example.englishqq.databinding.ItemStudyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding
    private lateinit var navController: NavController
    private val viewModel: StudyViewModel by viewModel()
    private val adapter: StudyAdapter = StudyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStudyBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.rvStudy.adapter = adapter

        viewModel.getStudyItem()

        setObservers()

        adapter.onItemClick {
            Toast.makeText(requireContext(), "Qaytaldan korseniz boladi :)", Toast.LENGTH_LONG).show()
        }
    }

    private fun setObservers() {
        viewModel.themeInfo.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
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