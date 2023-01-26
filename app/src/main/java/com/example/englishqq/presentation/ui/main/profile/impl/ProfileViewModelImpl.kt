package com.example.englishqq.presentation.ui.main.profile.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.remote.AuthHelper
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.presentation.ui.main.profile.ProfileViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ProfileViewModelImpl(private val authRepository: AuthRepository) : ProfileViewModel, ViewModel() {

    override val editProfileFlow: MutableSharedFlow<Resource<Unit>> = MutableSharedFlow()

    override val setProfileInfoFlow: MutableSharedFlow<List<String>> = MutableSharedFlow()

    override fun editProfile(newFirstName: String, newLastName: String) {
        viewModelScope.launch {
            editProfileFlow.emit(Resource.loading())
            authRepository.editProfile(newFirstName, newLastName,
                {
                    viewModelScope.launch {
                        editProfileFlow.emit(Resource.success(Unit))
                        authRepository.saveUserName(newFirstName)
                        authRepository.saveLastName(newLastName)
                    }
                },
                {
                    viewModelScope.launch {
                        editProfileFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }

    override fun setProfileInfo() {
        val listInfo = mutableListOf<String>()
        viewModelScope.launch {
            listInfo.add(authRepository.getUserName())
            listInfo.add(authRepository.getLastName())
            setProfileInfoFlow.emit(listInfo)
        }
    }
}