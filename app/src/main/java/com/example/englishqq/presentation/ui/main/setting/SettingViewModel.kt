package com.example.englishqq.presentation.ui.main.setting

import android.content.Context
import android.content.Intent
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface SettingViewModel {

    val editProfileFlow: SharedFlow<Resource<Unit>>

    val shareApplicationFlow: SharedFlow<Intent>

    val setProfileInfoFlow: SharedFlow<List<String>>

    val sendTelegramFlow: SharedFlow<Intent>

    val userLogoutFlow: SharedFlow<Unit>

    fun editProfile(newFirstName: String, newLastName: String)

    fun setProfileInfo()

    fun setOnShareApplication(shareTitle: String)

    fun sendTelegram(ctx: Context)

    fun userLogout()

}