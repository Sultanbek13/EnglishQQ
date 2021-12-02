package com.example.englishqq.ui.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)
        navController = Navigation.findNavController(view)
        var success = true

        binding.apply {
            btnSignUp.setOnClickListener {
                if(etFirstName.text.isNullOrEmpty()) {
                    etFirstName.error = getString(R.string.fill_the_field)
                    success = false
                }
                if(etLastName.text.isNullOrEmpty()) {
                    etLastName.error = getString(R.string.fill_the_field)
                    success = false
                }
                if(etEmail.text.isNullOrEmpty()) {
                    etEmail.error = getString(R.string.fill_the_field)
                    success = false
                }
                if(etPassword.text.isNullOrEmpty()) {
                    etPassword.error = getString(R.string.fill_the_field)
                    success = false
                }
                if(etRepeatPassword.text.isNullOrEmpty()) {
                    etRepeatPassword.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (!success) return@setOnClickListener

                if(etPassword.text.toString() != etRepeatPassword.text.toString()) {
                    etPassword.error = getString(R.string.password_not_match)
                    success = false
                } else {
                    viewModel.signUp(etEmail.text.toString(), etRepeatPassword.text.toString(), etFirstName.text.toString(), etLastName.text.toString())
                }
            }
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel.signUpStatus.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.LOADING -> {
                    setLoading(true)
                }
                ResourceState.SUCCESS -> {
                    setLoading(false)
                    navController.navigate(R.id.action_signUpFragment_to_mainFragment)
                }
                ResourceState.ERROR -> {
                    setLoading(false)
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            loading.isVisible = isLoading
            etEmail.isVisible = !isLoading
            etPassword.isVisible = !isLoading
        }
    }
}