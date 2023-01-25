package com.example.englishqq.presentation.ui.source.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentSplashBinding
import com.example.englishqq.presentation.ui.source.splash.impl.SplashViewModelImpl
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModel<SplashViewModelImpl>()
    private val binding: FragmentSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playSplashFlow.onEach {
            if (it) findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            else findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.apply {
            splashView.setAnimation(R.raw.lottie_splash_owl)
            splashView.playAnimation()
        }

        viewModel.playSplash()
    }
}