package com.example.englishqq.ui.test

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.englishqq.R
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.TestType
import com.example.englishqq.databinding.ItemStudyBinding
import com.example.englishqq.databinding.ItemTestBinding

class TestAdapter(private val context: Context, private val data: List<TestType>) : PagerAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var binding: ItemTestBinding

    override fun getCount(): Int {
        return data.size
    }

    fun itemCount(): Int {
        return data.size
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.item_test, container, false)
        binding = ItemTestBinding.bind(view)

        binding.tvEnglishWord.text = data[position].wordEnglish

        binding.contentView.setVideoURI(Uri.parse(data[position].contentUrl))
        binding.contentView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                (view as VideoView).start()
                binding.animationRe.playAnimation()
                if (hasFocus) {
                    binding.btnAnswer.setOnClickListener {
                        if (binding.etInput.text.toString() == data[position].rightAnswer) {
                            binding.tickAnimation.isVisible = true
                            binding.tickAnimation.playAnimation()
                            binding.winAnimation.isVisible = true
                            binding.winAnimation.playAnimation()
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                (view as VideoView).pause()
            }
        }

        container.addView(view)
        return binding.root
    }
}