package com.example.englishqq.presentation.ui.main.profile.impl

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_SUBJECT
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.BuildConfig
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.presentation.ui.main.profile.ProfileViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ProfileViewModelImpl(private val authRepository: AuthRepository) : ProfileViewModel,
    ViewModel() {

    override val editProfileFlow: MutableSharedFlow<Resource<Unit>> = MutableSharedFlow()

    override val shareApplicationFlow: MutableSharedFlow<Intent> = MutableSharedFlow()

    override val setProfileInfoFlow: MutableSharedFlow<List<String>> = MutableSharedFlow()

    override val sendTelegramFlow: MutableSharedFlow<Intent> = MutableSharedFlow()

    override val userLogoutFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

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

    override fun setOnShareApplication(shareTitle: String) {
        viewModelScope.launch {
            val intent = Intent()
            intent.action = ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(EXTRA_SUBJECT, shareTitle)
            shareApplicationFlow.emit(intent)
        }
    }

    override fun sendTelegram(ctx: Context) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(BuildConfig.TELEGRAM_URL)
            val appName = "org.telegram.messenger"
            try {
                if (isAppAvailable(ctx, appName)) {
                    intent.setPackage(appName)
                }
            } catch (e: PackageManager.NameNotFoundException) {
            }
            sendTelegramFlow.emit(intent)
        }
    }

    override fun userLogout() {
        viewModelScope.launch {
            authRepository.setStateAuth(false)
            userLogoutFlow.emit(Unit)
        }
    }

    private fun isAppAvailable(context: Context, appName: String): Boolean {
        val pm: PackageManager = context.packageManager
        return try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}