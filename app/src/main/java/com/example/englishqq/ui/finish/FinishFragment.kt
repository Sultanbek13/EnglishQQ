package com.example.englishqq.ui.finish

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentFinishBinding

class FinishFragment : Fragment(R.layout.fragment_finish) {

    private lateinit var binding: FragmentFinishBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFinishBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.cardViewReturnHome.setOnClickListener {
            navController.navigate(R.id.action_finishFragment_to_mainFragment)
        }

        binding.btnSetFeedback.setOnClickListener {
            if (binding.etFeedback.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Pikir bildirip ketin :)" , Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Pikiriniz ushin raxmet :)", Toast.LENGTH_LONG).show()
            }
        }

        binding.rightAnim.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                binding.rightAnim.playAnimation()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

        })

        binding.finishAnim.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                binding.finishAnim.playAnimation()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

        })
    }
}