package com.example.englishqq.ui.home

import android.annotation.SuppressLint
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.Material
import com.example.englishqq.databinding.ItemHomeBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var onItemCLick: (Material) -> Unit = {}

/*    private var stepInfo: (currentType: List<CurrentType>) -> Unit = {}
    fun setOnStepListener(onStepInfo: (currentType: List<CurrentType>) -> Unit) {
        this.stepInfo = onStepInfo
    }*/

    inner class ViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(model : Material) {

            binding.themeName.text = model.themeName

            Glide
                    .with(binding.root.context)
                    .load(model.imageUrl)
                    .into(binding.themeImage)

            binding.step.text = "1/${model.count}"
          /*  homeFragment.step {
                binding.step.text = it.step.toString()
            }*/

            binding.cardViewTheme.setOnClickListener {
                onItemCLick.invoke(model)
            }
        }
    }

    var models: MutableList<Material> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }

    fun onItemClick(onItemClick: (Material) -> Unit) {
        this.onItemCLick = onItemClick
    }
}