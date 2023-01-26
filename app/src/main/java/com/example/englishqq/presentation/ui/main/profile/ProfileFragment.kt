package com.example.englishqq.presentation.ui.main.profile

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_SUBJECT
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.englishqq.R
import com.example.englishqq.databinding.FragmentProfileBinding
import com.example.englishqq.presentation.ui.main.profile.impl.ProfileViewModelImpl
import com.example.englishqq.utils.ResourceState
import com.example.englishqq.utils.loading
import com.example.englishqq.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModelImpl by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        btnClick()
        viewModel.setProfileInfo()

    }

    private fun btnClick() {
        binding.btnEditProfile.setOnClickListener {
            var success = true
            val firstName = binding.etFirstName
            val lastName = binding.etLastName
            if (firstName.text.isNullOrEmpty()) {
                firstName.error = getString(R.string.fill_the_field)
                success = false
            }
            if (lastName.text.isNullOrEmpty()) {
                lastName.error = getString(R.string.fill_the_field)
                success = false
            }
            if(success) viewModel.editProfile(firstName.text.toString(), lastName.text.toString())
            else return@setOnClickListener
        }

/*        binding.rlShare.setOnClickListener {
            val intent = Intent(ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(EXTRA_SUBJECT, getString(R.string.share))
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

        binding.rlLogout.setOnClickListener {
            confirmationDialog()
        }*/

    }

    private fun setObserver() {
        viewModel.editProfileFlow.onEach {
            when (it.status) {
                ResourceState.LOADING -> loading(binding.loading, true)
                ResourceState.SUCCESS -> {
                    loading(binding.loading, false)
                    toast(requireContext(), "Profile janalandi :)")
                }
                ResourceState.ERROR -> {
                    loading(binding.loading, false)
                    toast(requireContext(), it.message ?: "")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.setProfileInfoFlow.onEach {
            binding.apply {
                etFirstName.setText(it[0])
                etLastName.setText(it[1])
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun logOut() {
        findNavController().navigate(R.id.action_mainFragment_to_signInFragment)
    }

    private fun confirmationDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .apply {
                setCancelable(false)

                setTitle("Kabinetden shig'iw")
                setMessage("Isenimingiz ka'milma?")
                setPositiveButton("Awa") { _, _ ->
                    logOut()
                }
                setNeutralButton("Yaq") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()

            }
    }
}