package com.example.englishqq.presentation.ui.source.auth.signup.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.presentation.ui.source.auth.signup.SignUpViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModelImpl(private val authRepository: AuthRepository) : SignUpViewModel,
    ViewModel() {

    override val signUpFlow: MutableSharedFlow<Resource<Any>> = MutableSharedFlow()

    override fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpFlow.emit(Resource.loading())
            authRepository.signUp(email, password,
                {
                    viewModelScope.launch {
                        addUserToDb(firstName, lastName)
                        authRepository.saveUserEmail(email)
                    }
                },
                {
                    viewModelScope.launch {
                        signUpFlow.emit(Resource.error(it))
                    }
                })
        }
    }

    override fun addUserToDb(firstName: String, lastName: String) {
        viewModelScope.launch {
            authRepository.addUserToDb(firstName, lastName,
                {
                    viewModelScope.launch {
                        signUpFlow.emit(Resource.success(Unit))
                        authRepository.saveUserName(firstName)
                        authRepository.saveLastName(lastName)
                        authRepository.setStateAuth(true)
                    }
                },
                {
                    viewModelScope.launch {
                        signUpFlow.emit(Resource.error("Please repeat again"))
                    }
                })
        }
    }
}