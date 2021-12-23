package com.example.englishqq.di

import com.example.englishqq.data.Settings
import com.example.englishqq.ui.auth.signin.SignInViewModel
import com.example.englishqq.data.helper.AuthHelper
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.ui.about.SettingViewModel
import com.example.englishqq.ui.auth.signup.SignUpViewModel
import com.example.englishqq.ui.dialog.DialogViewModel
import com.example.englishqq.ui.home.HomeViewModel
import com.example.englishqq.ui.study.StudyViewModel
import com.example.englishqq.ui.test.TestViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { AuthHelper(get(), get()) }
    single { ContentHelper(get(), get()) }
    single { Settings(androidContext()) }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { DialogViewModel(get()) }
    viewModel { StudyViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { TestViewModel(get()) }
}