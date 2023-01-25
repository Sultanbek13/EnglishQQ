package com.example.englishqq.presentation.ui.study

import com.example.englishqq.data.model.ContentData
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.flow.SharedFlow

interface StudyViewModel {

    val allContentFlow: SharedFlow<Resource<List<ContentData>>>

    fun getListContent(typeId: String)

}