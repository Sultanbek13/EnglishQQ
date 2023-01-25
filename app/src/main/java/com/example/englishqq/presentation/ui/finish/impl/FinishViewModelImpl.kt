package com.example.englishqq.presentation.ui.finish.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.repository.AuthRepository
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.presentation.ui.finish.FinishViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FinishViewModelImpl(
    private val contentRepository: ContentRepository,
    private val authRepository: AuthRepository
) : FinishViewModel, ViewModel() {

    override val saveFeedbackFlow: MutableSharedFlow<Resource<Unit>> = MutableSharedFlow()

    override fun saveFeedbackFromUser(typeId: String, feedback: String) {
        viewModelScope.launch(Dispatchers.IO) {
            contentRepository.saveFeedbackFromUser(
                typeId,
                authRepository.getUserEmail(),
                feedback,
                {
                    viewModelScope.launch {
                        saveFeedbackFlow.emit(Resource.success(Unit))
                    }
                },
                {
                    viewModelScope.launch {
                        saveFeedbackFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }
}