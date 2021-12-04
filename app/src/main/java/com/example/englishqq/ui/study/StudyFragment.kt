package com.example.englishqq.ui.study

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.englishqq.R
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.FragmentStudyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding
    private val viewModel: StudyViewModel by viewModel()
    private var viewPagerStudyAdapter: StudyAdapter? = null
    private lateinit var navController: NavController
    private val args: StudyFragmentArgs by navArgs()
    private val data = MutableLiveData<MutableList<CurrentType>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudyBinding.bind(view)
        navController = Navigation.findNavController(view)
        setObservers()

        data.observe(requireActivity(), {
            viewPagerStudyAdapter = StudyAdapter(requireContext(), it)
            binding.viewPagerStudy.adapter = viewPagerStudyAdapter
            val indicators = arrayOfNulls<ImageView>(viewPagerStudyAdapter!!.itemCount())
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(20, 20)
            layoutParams.setMargins(8, 0, 8, 0)
            for (i in indicators.indices) {
                indicators[i] = ImageView(requireContext())
                indicators[i].apply {
                    this?.setImageDrawable(
                            ContextCompat.getDrawable(requireContext(), R.drawable.degree)
                    )
                    this?.layoutParams = layoutParams
                }
                binding.indicatorsContainer.addView(indicators[i])
            }
            currentDotPosition(0)
            binding.viewPagerStudy.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    currentDotPosition(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        })

        viewModel.getStudyItem(args.typeId)

        binding.iconClose.setOnClickListener {
            navController.navigate(R.id.action_studyFragment_to_mainFragment)
        }
    }

    private fun currentDotPosition(index: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.color_dot)
                )
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.degree)
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.themeInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    data.postValue(it.data!!.toMutableList())
                }
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
            }
        }
    }
}