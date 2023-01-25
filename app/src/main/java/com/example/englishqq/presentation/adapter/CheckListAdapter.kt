package com.example.englishqq.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishqq.R
import com.example.englishqq.data.model.ContentData
import com.example.englishqq.databinding.ItemListWordBinding

class CheckListAdapter :
    ListAdapter<ContentData, CheckListAdapter.ViewHolder>(CheckListCompressor) {

    inner class ViewHolder(private val binding: ItemListWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvEnglishWord.text = getItem(absoluteAdapterPosition).wordEnglish
            binding.tvQQWord.text = getItem(absoluteAdapterPosition).wordQQ
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListWordBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_word, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}

object CheckListCompressor : DiffUtil.ItemCallback<ContentData>() {
    override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.themeName == newItem.themeName &&
                oldItem.themeId == newItem.themeId &&
                oldItem.title == newItem.title &&
                oldItem.wordEnglish == newItem.wordEnglish &&
                oldItem.wordQQ == newItem.wordQQ
    }
}