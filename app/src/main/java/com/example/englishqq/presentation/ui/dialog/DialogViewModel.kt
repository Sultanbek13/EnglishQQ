package com.example.englishqq.presentation.ui.dialog

import com.example.englishqq.data.model.ContentData
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface DialogViewModel {

    val allContentFlow: SharedFlow<Resource<List<ContentData>>>

    fun getListContent(typeId: String)

}