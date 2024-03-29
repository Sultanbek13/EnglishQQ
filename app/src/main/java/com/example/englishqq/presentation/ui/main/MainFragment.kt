package com.example.englishqq.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentMainBinding
import com.example.englishqq.presentation.ui.main.setting.SettingFragment
import com.example.englishqq.presentation.ui.main.home.HomeFragment

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer)

        binding.bnv.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
        binding.bnv.add(MeowBottomNavigation.Model(2, R.drawable.ic_settings))

        binding.bnv.show(1, true)
        loadFragment(HomeFragment())

        binding.bnv.setOnShowListener {
            when (it.id) {
                1 -> {
                    loadFragment(HomeFragment())
                }
                2 -> {
                    loadFragment(SettingFragment())
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment?) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment!!).commit()
    }
}