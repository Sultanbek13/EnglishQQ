package com.example.englishqq.ui.study.mainstudy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentMainStudyBinding

class MainStudyFragment : Fragment(R.layout.fragment_main_study) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentMainStudyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainStudyBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)

       binding.iconClose.setOnClickListener {
            navController.navigate(R.id.action_mainStudyFragment_to_mainFragment)
        }
    }
}