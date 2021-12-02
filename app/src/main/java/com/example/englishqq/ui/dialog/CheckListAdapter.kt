package com.example.englishqq.ui.dialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.databinding.ItemListWordBinding
import org.w3c.dom.Text

class CheckListAdapter : RecyclerView.Adapter<CheckListAdapter.ViewHolder>() {

/*    private var onClick: (typeId: CurrentType) -> Unit = {
    }

    fun setOnClickListener(onCLick: (typeId: CurrentType) -> Unit) {
        this.onClick = onCLick
    }*/

    inner class ViewHolder(private val binding : ItemListWordBinding) : RecyclerView.ViewHolder((binding.root)) {
        fun populateModel(studyList: CurrentType) {
            binding.tvEnglishWord.text = studyList.wordEnglish
            binding.tvQQWord.text = studyList.wordQQ
        }
    }

    var models: MutableList<CurrentType> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }
}