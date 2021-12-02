package com.example.englishqq.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishqq.data.helper.ContentHelper
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.data.model.Resource

class DialogViewModel(private val contentHelper: ContentHelper) : ViewModel() {

    private val mutableCurrentWords: MutableLiveData<Resource<List<CurrentType>>> = MutableLiveData()
    val words: LiveData<Resource<List<CurrentType>>> get() = mutableCurrentWords

    fun getListWords(typeId: String?) {
        mutableCurrentWords.value = Resource.loading()
        contentHelper.getListWords(typeId,
                {
                    mutableCurrentWords.value = Resource.success(it)
                },
                {
                    mutableCurrentWords.value = Resource.error(it)
                }
        )
    }
}