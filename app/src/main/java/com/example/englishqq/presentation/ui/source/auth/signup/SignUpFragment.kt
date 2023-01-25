package com.example.englishqq.presentation.ui.source.auth.signup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentSignUpBinding
import com.example.englishqq.presentation.ui.source.auth.signup.impl.SignUpViewModelImpl
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Pulse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding: FragmentSignUpBinding by viewBinding()
    private val viewModel: SignUpViewModelImpl by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setOnClickSignUp()

    }

    private fun setObserver() {
        viewModel.signUpFlow.onEach {
            when (it.status) {
                ResourceState.LOADING -> {
                    loading(binding.loading, true)
                }
                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToMainFragment())
                }
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setOnClickSignUp() {

        binding.apply {
            btnSignUp.setOnClickListener {

                var success = true

                if (etFirstName.text.isNullOrEmpty()) {
                    etFirstName.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (etLastName.text.isNullOrEmpty()) {
                    etLastName.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (etEmail.text.isNullOrEmpty()) {
                    etEmail.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (etPassword.text.isNullOrEmpty()) {
                    etPassword.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (etRepeatPassword.text.isNullOrEmpty()) {
                    etRepeatPassword.error = getString(R.string.fill_the_field)
                    success = false
                }

                if (etPassword.text.toString() != etRepeatPassword.text.toString()) {
                    etPassword.error = getString(R.string.password_not_match)
                    etRepeatPassword.error = getString(R.string.password_not_match)
                    success = false
                }
                if (!success) return@setOnClickListener
                else {
                    viewModel.signUp(
                        etEmail.text.toString(),
                        etRepeatPassword.text.toString(),
                        etFirstName.text.toString(),
                        etLastName.text.toString()
                    )
                }
            }
        }
    }
}