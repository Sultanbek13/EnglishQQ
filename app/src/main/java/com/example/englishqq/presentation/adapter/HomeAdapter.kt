package com.example.englishqq.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishqq.R
import com.example.englishqq.data.model.CategoryData
import com.example.englishqq.databinding.ItemCategoryBinding

class HomeAdapter : ListAdapter<CategoryData, HomeAdapter.ViewHolder>(HomeAdapterCompressor) {

    private var onClickListener: ((String, String) -> Unit)? = null

    fun setOnClickListener(block: (String, String) -> Unit) {
        onClickListener = block
    }

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (Integer.parseInt(getItem(absoluteAdapterPosition).count) > 1) {
                    onClickListener?.invoke(
                        getItem(absoluteAdapterPosition).typeId,
                        getItem(absoluteAdapterPosition).themeName
                    )
                }
            }
        }

        fun bind() {
            binding.apply {

                if (Integer.parseInt(getItem(absoluteAdapterPosition).count) < 10) {
                    binding.llCategory.setBackgroundResource(R.color.not_item_category_color)
                    tvPrevention.isVisible = true
                }

                themeName.text = getItem(absoluteAdapterPosition).themeName

                Glide
                    .with(binding.root.context)
                    .load(getItem(absoluteAdapterPosition).imageUrl)
                    .into(binding.themeImage)

                step.text = getItem(absoluteAdapterPosition).count
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}

object HomeAdapterCompressor : DiffUtil.ItemCallback<CategoryData>() {
    override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
        return oldItem.typeId == newItem.typeId
    }

    override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
        return oldItem.typeId == newItem.typeId &&
                oldItem.count == newItem.count &&
                oldItem.imageUrl == newItem.imageUrl &&
                oldItem.themeName == newItem.themeName
    }
}