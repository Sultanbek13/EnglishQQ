package com.example.englishqq.presentation.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.englishqq.data.model.TestData
import com.example.englishqq.databinding.ItemTestBinding
import com.example.englishqq.utils.playConfettiAnim

class TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private val onLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingTest: LiveData<Boolean> get() = onLoadingLiveData

    var models: MutableList<TestData> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TestData) {

            binding.apply {

                onLoadingLiveData.value = true

                tvEnglishWord.text = item.wordEnglish
                contentView.setVideoURI(Uri.parse(item.contentUrl))

                contentView.setOnPreparedListener {
                    onLoadingLiveData.value = false
                    contentView.start()
                }

                btnCheckAnswer.setOnClickListener {
                    when (binding.etInput.text.toString().trim() == item.rightAnswer) {
                        true -> {
                            playConfettiAnim(confettiView)
                            wrongAnimation.isVisible = false
                            wrongAnimation.pauseAnimation()
                            tickAnimation.isVisible = true
                            tickAnimation.playAnimation()
                            arrow.isVisible = false
                            arrow.pauseAnimation()
                        }
                        false -> {
                            tickAnimation.isVisible = false
                            tickAnimation.pauseAnimation()
                            wrongAnimation.isVisible = true
                            wrongAnimation.playAnimation()
                            arrow.isVisible = true
                            arrow.playAnimation()
                        }
                    }
                }
            }
        }
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(models[position])

    override fun getItemCount(): Int = models.size
}