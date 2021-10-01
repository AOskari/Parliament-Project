package com.example.parliamentproject.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
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
class SettingsFragment : Fragment() {

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

        updateUI()

        // Setting an observer for updating the radio buttons.
        settingsViewModel.getSettings().observe(viewLifecycleOwner, { s ->
            s.let {
                settingsViewModel.settings = it
                updateUI()
            }
        })

        // Setting a onClickListener to the back button.
        binding.settingsBack.setOnClickListener {
            updateSettings()
            val action = SettingsFragmentDirections.actionSettingsFragmentToMemberListFragment()
            findNavController().navigate(action)
        }

        // Updating the minAge and maxAge texts according to the RangeSlider's position.
        binding.ageSlider.addOnChangeListener { slider, _, _ ->
            binding.settingsMinAge.text = slider.values[0].toInt().toString()
            binding.settingsMaxAge.text = slider.values[1].toInt().toString()
        }

        return binding.root
    }

    /** Updates the settings with the radio buttons and returns it. */
    private fun updateSettings() {

        val ageValues = binding.ageSlider.values

        Log.d("SettingsFragment", "Updating settings. minAge: ${ageValues[0]}, maxAge: ${ageValues[1]}")

        val newSettings = Settings( binding.kdpRadio.isChecked, binding.keskRadio.isChecked, binding.kokRadio.isChecked,
            binding.liikRadio.isChecked, binding.psRadio.isChecked, binding.rRadio.isChecked, binding.sdRadio.isChecked,
            binding.vasRadio.isChecked, binding.vihrRadio.isChecked, ageValues[0].toInt(), ageValues[1].toInt())



        settingsViewModel.let {
            settingsViewModel.applicationScope.launch {
                settingsViewModel.updateSettings(newSettings)
            }
        }
    }

    /** Updates the RadioButtons and RangeSlider. */
    private fun updateUI() {

        binding.kdpRadio.isChecked = settingsViewModel.settings.showKDP
        binding.keskRadio.isChecked = settingsViewModel.settings.showKesk
        binding.kokRadio.isChecked = settingsViewModel.settings.showKok
        binding.liikRadio.isChecked = settingsViewModel.settings.showLiik
        binding.psRadio.isChecked = settingsViewModel.settings.showPS
        binding.rRadio.isChecked = settingsViewModel.settings.showRKP
        binding.sdRadio.isChecked = settingsViewModel.settings.showSDP
        binding.vasRadio.isChecked = settingsViewModel.settings.showVas
        binding.vihrRadio.isChecked = settingsViewModel.settings.showVihr

        binding.ageSlider.values = mutableListOf(settingsViewModel.settings.minAge.toFloat(), settingsViewModel.settings.maxAge.toFloat())

        binding.settingsMinAge.text = settingsViewModel.settings.minAge.toString()
        binding.settingsMaxAge.text = settingsViewModel.settings.maxAge.toString()
    }

}