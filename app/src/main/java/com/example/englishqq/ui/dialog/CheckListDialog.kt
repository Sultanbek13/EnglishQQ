package com.example.englishqq.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.englishqq.R
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.DialogCheckListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListDialog: BottomSheetDialogFragment() {
    private var savedViewInstance: View? = null
    private lateinit var binding: DialogCheckListBinding
    private val adapter : CheckListAdapter = CheckListAdapter()
    private val viewModel: DialogViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        return if (savedInstanceState != null) {
            savedViewInstance
        } else {
            savedViewInstance = inflater.inflate(R.layout.dialog_check_list, container, true)
            savedViewInstance
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogCheckListBinding.bind(view)
        binding.rvStudyList.adapter = adapter

        viewModel.getListWords()

        setObservers()

        view.findViewById<Button>(R.id.btnStart).setOnClickListener {
            findNavController().navigate(
                    R.id.action_checkListDialog_to_mainStudyFragment2)
        }
    }

    private fun setObservers() {
        viewModel.words.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    adapter.models = it.data!!.toMutableList()
                }
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}