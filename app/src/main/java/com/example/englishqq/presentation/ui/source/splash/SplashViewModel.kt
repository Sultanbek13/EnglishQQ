package com.example.englishqq.presentation.ui.source.splash

import kotlinx.coroutines.flow.SharedFlow

interface SplashViewModel {

    val playSplashFlow: SharedFlow<Boolean>

    val getAuthStatusFlow: SharedFlow<Boolean>

    fun playSplash()

}