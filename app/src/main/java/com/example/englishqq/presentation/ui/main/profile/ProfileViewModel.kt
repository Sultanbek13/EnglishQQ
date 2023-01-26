package com.example.englishqq.presentation.ui.main.profile

import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface ProfileViewModel {

    val editProfileFlow: SharedFlow<Resource<Unit>>

    val setProfileInfoFlow: SharedFlow<List<String>>

    fun editProfile(newFirstName: String, newLastName: String)

    fun setProfileInfo()

}