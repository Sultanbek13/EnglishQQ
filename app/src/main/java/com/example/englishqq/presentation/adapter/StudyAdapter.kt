package com.example.englishqq.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.databinding.ItemStudyBinding
import com.squareup.okhttp.internal.Platform.get


class StudyAdapter(private val context: Context) : RecyclerView.Adapter<StudyAdapter.ViewHolder>() {

    private val onLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingStudy: LiveData<Boolean> get() = onLoadingLiveData

    var models: MutableList<ContentData> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemStudyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ContentData) {

            onLoadingLiveData.value = true

            binding.apply {
                tvEnglishWord.text = data.wordEnglish
                tvQQWord.text = data.wordQQ
                contentView.setVideoURI(Uri.parse(data.contentUrl))

                val mediaController = MediaController(context)
                contentView.setMediaController(mediaController)
                mediaController.setAnchorView(binding.contentView)

                contentView.setOnPreparedListener {
                    onLoadingLiveData.value = false
                    contentView.start()
                }
            }
        }
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemStudyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(models[position])
    }

    override fun getItemCount(): Int = models.size
}