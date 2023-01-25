package com.example.englishqq.presentation.ui.source.splash.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.presentation.ui.source.splash.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SplashViewModelImpl(private val authRepository: AuthRepository) : SplashViewModel, ViewModel() {

    override val playSplashFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override val getAuthStatusFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override fun playSplash() {
        viewModelScope.launch {
            delay(2000)
            if(authRepository.getStateAuth()) playSplashFlow.emit(true)
            else playSplashFlow.emit(false)
        }
    }
}