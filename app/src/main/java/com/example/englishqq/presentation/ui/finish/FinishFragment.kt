package com.example.englishqq.presentation.ui.finish

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentFinishBinding
import com.example.englishqq.presentation.ui.finish.impl.FinishViewModelImpl
import com.example.englishqq.utils.AnimListener
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinishFragment : Fragment(R.layout.fragment_finish) {

    private val binding: FragmentFinishBinding by viewBinding()
    private val viewModel: FinishViewModelImpl by viewModel()
    private val args: FinishFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            cardViewReturnHome.setOnClickListener {
                findNavController().navigate(R.id.action_finishFragment_to_mainFragment)
            }

            setObserver()

            animManWithFlag.addAnimatorListener(object : AnimListener {
                override fun onAnimationEnd(p0: Animator?) {
                    animManWithFlag.playAnimation()
                }
            })

            btnSendFeedback.setOnClickListener {
                if (etFeedback.text.isNullOrEmpty()) {
                    toast(requireContext(), getString(R.string.write_something_please))
                } else {
                    val feedback = etFeedback.text.toString()
                    saveFeedback(feedback)
                    etFeedback.text?.clear()
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.saveFeedbackFlow.onEach {
            when (it.status) {
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
                ResourceState.LOADING -> loading(binding.loading, true)

                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    findNavController().navigate(FinishFragmentDirections.actionFinishFragmentToMainFragment())
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun saveFeedback(feedback: String) {
        viewModel.saveFeedbackFromUser(args.themeName, feedback)
    }
}