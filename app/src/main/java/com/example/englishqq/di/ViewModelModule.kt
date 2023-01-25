package com.example.englishqq.di

import com.example.englishqq.presentation.ui.about.SettingViewModel
import com.example.englishqq.presentation.ui.dialog.impl.DialogViewModelImpl
import com.example.englishqq.presentation.ui.finish.impl.FinishViewModelImpl
import com.example.englishqq.presentation.ui.home.impl.HomeViewModelImpl
import com.example.englishqq.presentation.ui.source.auth.signin.impl.SignInViewModelImpl
import com.example.englishqq.presentation.ui.source.auth.signup.impl.SignUpViewModelImpl
import com.example.englishqq.presentation.ui.source.splash.impl.SplashViewModelImpl
import com.example.englishqq.presentation.ui.study.impl.StudyViewModelImpl
import com.example.englishqq.presentation.ui.test.impl.TestViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModelImpl(get()) }
    viewModel { SignInViewModelImpl(get()) }
    viewModel { SignUpViewModelImpl(get()) }
    viewModel { HomeViewModelImpl(get(), get()) }
    viewModel { DialogViewModelImpl(get()) }
    viewModel { StudyViewModelImpl(get()) }
    viewModel { TestViewModelImpl(get()) }
    viewModel { FinishViewModelImpl(get(), get()) }
    viewModel { SettingViewModel(get()) }
}