package com.example.englishqq.ui.test

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.englishqq.R
import com.example.englishqq.data.model.CurrentType
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.data.model.TestType
import com.example.englishqq.databinding.FragmentTestBinding
import com.example.englishqq.ui.study.StudyAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding
    private lateinit var navController: NavController
    private val viewModel: TestViewModel by viewModel()
    private var viewPagerTestAdapter: TestAdapter? = null
    private val data = MutableLiveData<MutableList<TestType>>()
    private val args: TestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTestBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.iconClose.setOnClickListener {
            requireActivity().onBackPressed()
        }

        data.observe(requireActivity(), {
            viewPagerTestAdapter = TestAdapter(requireContext(), it)
            binding.viewPagerTest.adapter = viewPagerTestAdapter
            val indicators = arrayOfNulls<ImageView>(viewPagerTestAdapter!!.itemCount())
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(20, 20)
            layoutParams.setMargins(8, 0, 8, 0)
            binding.indicatorsContainer.removeAllViews()
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
            binding.viewPagerTest.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    currentDotPosition(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        })

        setObservers()
        viewModel.getTestItem(args.testId)
    }

    private fun currentDotPosition(index: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.color_dot)
                )
                if (i == childCount - 1) {
                    binding.btnFuther.isVisible = true
                    binding.btnFuther.setOnClickListener {
                        navController.navigate(R.id.action_testFragment_to_finishFragment)
                    }
                } else {
                    binding.btnFuther.isVisible = false
                }
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.degree)
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.testInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    data.postValue(it.data!!.toMutableList())
                }
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}