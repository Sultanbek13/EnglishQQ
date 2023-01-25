package com.example.englishqq.presentation.ui.dialog.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.data.remote.ContentHelper
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.presentation.ui.dialog.DialogViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DialogViewModelImpl(private val contentRepository: ContentRepository) : DialogViewModel, ViewModel() {

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