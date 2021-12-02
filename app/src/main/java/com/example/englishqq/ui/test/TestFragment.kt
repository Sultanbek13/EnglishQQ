package com.example.englishqq.ui.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentTestBinding

class TestFragment : Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTestBinding.bind(view)
        navController = Navigation.findNavController(view)
    }
}