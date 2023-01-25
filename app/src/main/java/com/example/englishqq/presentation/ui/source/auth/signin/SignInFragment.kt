package com.example.englishqq.presentation.ui.source.auth.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentSignInBinding
import com.example.englishqq.presentation.ui.source.auth.signin.impl.SignInViewModelImpl
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Pulse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding()
    private val viewModel: SignInViewModelImpl by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickBtn()
        setObserver()

    }

    private fun setOnClickBtn() {
        binding.apply {
            tvRegisterNow.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }

            btnNext.setOnClickListener {
                var success = true
                if (etEmail.text.isNullOrEmpty()) {
                    success = false
                    etEmail.error = getString(R.string.fill_the_field)
                }
                if (etPassword.text.isNullOrEmpty()) {
                    success = false
                    etPassword.error = getString(R.string.fill_the_field)
                }
                if (success) viewModel.signIn(etEmail.text.toString(), etPassword.text.toString())
                else return@setOnClickListener
            }
        }
    }

    private fun setObserver() {
        viewModel.signInFlow.onEach {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment())
                }
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
                ResourceState.LOADING -> {
                    loading(binding.loading, true)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}