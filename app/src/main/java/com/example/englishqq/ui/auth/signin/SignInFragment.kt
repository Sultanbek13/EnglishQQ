package com.example.englishqq.ui.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.data.Settings
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in){

    private lateinit var binding: FragmentSignInBinding
    private lateinit var navController: NavController
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)
        navController = Navigation.findNavController(view)
        setObservers()

        binding.apply {
            tvRegisterNow.setOnClickListener {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }

            binding.btnNext.setOnClickListener {
                var success = true
                if (etEmail.text.isNullOrEmpty()) {
                    success = false
                    etEmail.error = getString(R.string.fill_the_field)
                }
                if (etPassword.text.isNullOrEmpty()) {
                    success = false
                    etPassword.error = getString(R.string.fill_the_field)
                }
                if (!success) return@setOnClickListener
                viewModel.signIn(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }


    private fun setObservers() {
        viewModel.signInStatus.observe(viewLifecycleOwner, {
            when(it.status) {
                ResourceState.SUCCESS -> {
                    navController.navigate(R.id.action_signInFragment_to_mainFragment)
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.LOADING -> {
                    setLoading(true)
                }
            }
        })
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            loading.isVisible = isLoading
            etEmail.isVisible = !isLoading
            etPassword.isVisible = !isLoading
        }
    }
}