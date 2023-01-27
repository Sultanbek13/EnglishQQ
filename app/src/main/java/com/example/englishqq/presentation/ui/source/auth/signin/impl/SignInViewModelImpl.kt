package com.example.englishqq.presentation.ui.source.auth.signin.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.presentation.ui.source.auth.signin.SignInViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignInViewModelImpl(private val authRepository: AuthRepository) : SignInViewModel,
    ViewModel() {

    override val signInFlow: MutableSharedFlow<Resource<Any?>> = MutableSharedFlow()

    override fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            signInFlow.emit(Resource.loading())
            authRepository.signIn(email, password,
                {
                    viewModelScope.launch {
                        saveUserInfoAndSignIn()
                    }
                },
                {
                    viewModelScope.launch {
                        signInFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }

    override fun saveUserInfoAndSignIn() {
        viewModelScope.launch {
            authRepository.getUserData(
                {
                    viewModelScope.launch {
                        if (it != null) {
                            authRepository.saveUserName(it.firstName)
                            authRepository.saveLastName(it.lastName)
                            authRepository.setStateAuth(true)
                            signInFlow.emit(Resource.success(null))
                        }
                    }
                },
                {
                    viewModelScope.launch {
                        signInFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }
}