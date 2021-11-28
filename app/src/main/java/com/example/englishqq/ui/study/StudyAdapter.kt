package com.example.englishqq.ui.study

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.databinding.ItemStudyBinding

class StudyAdapter : RecyclerView.Adapter<StudyAdapter.ViewHolder>() {

    private var onItemCLick: (CurrentType) -> Unit = {}

/*
    private var onClick: (currentType: CurrentType) -> Unit = {
    }
    fun setOnClickListener(onCLick: (currentType: CurrentType) -> Unit) {
        this.onClick = onCLick
    }
*/

    inner class ViewHolder(private val binding: ItemStudyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(currentType: CurrentType) {

            binding.tvEnglishWord.text = currentType.wordEnglish
            binding.tvQQWord.text = currentType.wordQQ
            binding.tvTitle.text = currentType.title

            val uri = Uri.parse(currentType.contentUrl)
            binding.contentView.setVideoURI(uri)
            binding.contentView.requestFocus()
            binding.contentView.start()

            binding.animationRe.setOnClickListener {
                onItemCLick.invoke(currentType)
                binding.contentView.setVideoURI(uri)
                binding.contentView.requestFocus()
                binding.contentView.start()
                binding.animationRe.playAnimation()
            }
        }
    }

    var models: MutableList<CurrentType> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =  ItemStudyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }

    fun onItemClick(onItemClick: (CurrentType) -> Unit) {
        this.onItemCLick = onItemClick
    }
}