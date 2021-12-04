package com.example.englishqq.ui.study

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.englishqq.R
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.databinding.ItemStudyBinding

class StudyAdapter(private val context: Context, private val data: List<CurrentType>) : PagerAdapter() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var binding: ItemStudyBinding

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
        val view = layoutInflater.inflate(R.layout.item_study, container, false)
        binding = ItemStudyBinding.bind(view)

        binding.tvTitle.text = data[position].title
        binding.tvEnglishWord.text = data[position].wordEnglish
        binding.tvQQWord.text = data[position].wordQQ
        binding.contentView.setVideoURI(Uri.parse(data[position].contentUrl))

        binding.contentView.setOnFocusChangeListener { view, hasFocus ->
            Log.d("tekseriw", "position=$position, focus=$hasFocus ")
            if (hasFocus) {
                (view as VideoView).start()
                binding.animationRe.playAnimation()
            } else {
                (view as VideoView).pause()
            }
        }

        container.addView(view)
        return binding.root
    }
}