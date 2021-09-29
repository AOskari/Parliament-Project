package com.example.parliamentproject.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.view_models.*
import com.example.parliamentproject.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** A Fragment subclass, which is used for updating the single Settings object in the Room database. */
class SettingsFragment : DialogFragment() {

    private lateinit var binding : FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var settingsViewModelFactory: SettingsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Getting or creating an instance of the SettingsViewModel depending if there is one or not.
        settingsViewModelFactory = SettingsViewModelFactory((activity?.application as MPApplication).settingsRepository)
        settingsViewModel = ViewModelProvider(this, settingsViewModelFactory).get(SettingsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        // Setting an observer for updating the radio buttons.
        settingsViewModel.getSettings().observe(viewLifecycleOwner, { s ->
            s.let {
                settingsViewModel.settings = it
                updateRadioButtons()
            }
        })

        // Setting a onClickListener to the back button.
        binding.settingsBack.setOnClickListener {
            updateSettings()
            val action = SettingsFragmentDirections.actionSettingsFragmentToMemberListFragment()
            findNavController().navigate(action)
            dismiss()
        }

        // Setting the background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    /** Updates the settings with the radio buttons and returns it. */
    private fun updateSettings() {

        val newSettings = Settings( binding.kdpRadio.isChecked, binding.keskRadio.isChecked, binding.kokRadio.isChecked,
            binding.liikRadio.isChecked, binding.psRadio.isChecked, binding.rRadio.isChecked, binding.sdRadio.isChecked,
            binding.vasRadio.isChecked, binding.vihrRadio.isChecked )

        settingsViewModel.let {
            settingsViewModel.applicationScope.launch {
                settingsViewModel.updateSettings(newSettings)
            }
        }
    }

    /** Updates the radiobuttons. */
    private fun updateRadioButtons() {

        binding.kdpRadio.isChecked = settingsViewModel.settings.showKDP
        binding.keskRadio.isChecked = settingsViewModel.settings.showKesk
        binding.kokRadio.isChecked = settingsViewModel.settings.showKok
        binding.liikRadio.isChecked = settingsViewModel.settings.showLiik
        binding.psRadio.isChecked = settingsViewModel.settings.showPS
        binding.rRadio.isChecked = settingsViewModel.settings.showRKP
        binding.sdRadio.isChecked = settingsViewModel.settings.showSDP
        binding.vasRadio.isChecked = settingsViewModel.settings.showVas
        binding.vihrRadio.isChecked = settingsViewModel.settings.showVihr
    }

}