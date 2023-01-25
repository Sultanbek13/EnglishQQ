package com.example.englishqq.presentation.ui.test.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishqq.data.model.TestData
import com.example.englishqq.data.repository.ContentRepository
import com.example.englishqq.presentation.ui.test.TestViewModel
import com.example.englishqq.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TestViewModelImpl(private val contentRepository: ContentRepository) : TestViewModel,
    ViewModel() {

    override val allTestFlow: MutableSharedFlow<Resource<List<TestData>>> = MutableSharedFlow()

    override fun getListTestContent(typeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            contentRepository.getListTestContent(typeId,
                {
                    viewModelScope.launch {
                        allTestFlow.emit(Resource.success(it))
                    }
                },
                {
                    viewModelScope.launch {
                        allTestFlow.emit(Resource.error(it))
                    }
                }
            )
        }
    }
}