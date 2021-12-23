package com.example.englishqq.ui.about

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.englishqq.R
import com.example.englishqq.data.Settings
import com.example.englishqq.data.model.ResourceState
import com.example.englishqq.data.model.User
import com.example.englishqq.databinding.FragmentAboutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var navController: NavController
    private val viewModel: SettingViewModel by viewModel()
    private lateinit var cUser: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAboutBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel.getUser()

        setObservers()

        binding.rlShare.setOnClickListener {
            val intent = Intent(ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(EXTRA_SUBJECT,getString(R.string.share))
            startActivity(Intent.createChooser(intent,getString(R.string.share)))
        }

        binding.rlLogout.setOnClickListener {
            confirmationDialog()
        }

        binding.btnEditProfile.setOnClickListener {
            cUser.firstName = binding.etNewFirstName.text.toString()
            cUser.lastName = binding.etNewLastName.text.toString()
            viewModel.editProfile(cUser)
        }
    }

    private fun setObservers() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    cUser = it.data!!
                    binding.etNewFirstName.setText(cUser.firstName)
                    binding.etNewLastName.setText(cUser.lastName)
                }
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
            }
        }

        viewModel.profileInfo.observe(viewLifecycleOwner) {
            when(it.status) {
                ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
                ResourceState.SUCCESS -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), "Profiliniz jag'alandi :)", Toast.LENGTH_LONG).show()
                }
                ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun logOut() {
        val settings: Settings = Settings(requireContext())
        navController.navigate(R.id.action_mainFragment_to_signInFragment)
        settings.signedIn = false
    }

    private fun confirmationDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                .apply {
                    setCancelable(false)

                    setTitle("Kabinetden shigiw")
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