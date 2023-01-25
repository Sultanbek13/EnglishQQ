package com.example.englishqq.presentation.ui.finish

import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface FinishViewModel {

    val saveFeedbackFlow: SharedFlow<Resource<Unit>>

    fun saveFeedbackFromUser(
        typeId: String,
        feedback: String
    )
}