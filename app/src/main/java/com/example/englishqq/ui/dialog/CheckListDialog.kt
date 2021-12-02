package com.example.englishqq.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishqq.R
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.databinding.DialogCheckListBinding
import com.example.englishqq.ui.auth.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckListDialog: BottomSheetDialogFragment() {

    private var savedViewInstance: View? = null
    private lateinit var binding:  DialogCheckListBinding
    private val adapter : CheckListAdapter = CheckListAdapter()
    private val viewModel: DialogViewModel by viewModel()
   // private lateinit var mainFragment: MainFragment
    private lateinit var navController: NavController
    private lateinit var bundle: String

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
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        bundle = arguments?.getString("typeId").toString()
        val bundleThemeName = arguments?.getString("themeId").toString()

        viewModel.getListWords(bundle)

        setObservers()

        binding.tvThemeName.text = bundleThemeName

        binding.btnStart.setOnClickListener {
            val setBundle = bundleOf("id" to bundle)
            navController.navigate(R.id.action_checkListDialog_to_mainStudyFragment, setBundle)
            Log.d("typeId", setBundle.toString())
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