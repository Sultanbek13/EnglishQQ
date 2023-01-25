package com.example.englishqq.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.DialogCheckListBinding
import com.example.englishqq.presentation.adapter.CheckListAdapter
import com.example.englishqq.presentation.ui.dialog.impl.DialogViewModelImpl
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListDialog : BottomSheetDialogFragment() {

    private var savedViewInstance: View? = null
    private val binding: DialogCheckListBinding by viewBinding()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CheckListAdapter() }
    private val viewModel: DialogViewModelImpl by viewModel()
    private val navArgs: CheckListDialogArgs by navArgs()

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

        binding.rvStudyList.adapter = adapter
        binding.tvThemeName.text = navArgs.themeName

        setObserver()

        binding.btnStart.setOnClickListener {
            findNavController().navigate(
                CheckListDialogDirections.actionCheckListDialogToStudyFragment(
                    navArgs.typeId
                )
            )
        }

        viewModel.getListContent(navArgs.typeId)
    }

    private fun setObserver() {
        viewModel.allContentFlow.onEach {
            when (it.status) {
                ResourceState.LOADING -> {
                    loading(binding.loading, true)
                }
                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    adapter.submitList(it.data)
                }
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}