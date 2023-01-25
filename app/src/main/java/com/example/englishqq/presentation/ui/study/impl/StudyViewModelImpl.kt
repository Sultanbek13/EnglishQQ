package com.example.englishqq.presentation.ui.study.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.presentation.ui.study.StudyViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class StudyViewModelImpl(private val contentRepository: ContentRepository) : StudyViewModel, ViewModel() {

    override val allContentFlow: MutableSharedFlow<Resource<List<ContentData>>> =
        MutableSharedFlow()

    override fun getListContent(typeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            allContentFlow.emit(Resource.loading())
            contentRepository.getListContent(
                typeId,
                {
                    viewModelScope.launch {
                        allContentFlow.emit(Resource.success(it))
                    }
                },
                {
                    viewModelScope.launch {
                        allContentFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }
}