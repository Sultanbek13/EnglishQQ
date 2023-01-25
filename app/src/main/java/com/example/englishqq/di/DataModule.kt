package com.example.englishqq.di

import com.example.englishqq.data.pref.SharedPref
import com.example.englishqq.data.pref.impl.SharedPrefImpl
import com.example.englishqq.data.remote.AuthHelper
import com.example.englishqq.data.remote.ContentHelper
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.data.repository.impl.AuthRepositoryImpl
import com.example.englishqq.data.repository.impl.ContentRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { AuthHelper(get(), get()) }
    single { ContentHelper(get(), get()) }
    single<SharedPref> { SharedPrefImpl(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<ContentRepository> { ContentRepositoryImpl(get()) }
}