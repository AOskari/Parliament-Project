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
import androidx.navigation.fragment.findNavController
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.view_models.MemberListViewModel
import com.example.parliamentproject.data.view_models.MemberListViewModelFactory
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsFragment : DialogFragment() {

    private lateinit var binding : FragmentSettingsBinding
    private var settings = Settings()
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val memberListViewModel : MemberListViewModel by viewModels {
        MemberListViewModelFactory((activity?.application as MPApplication).memberRepository,
            (activity?.application as MPApplication).settingsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        updateRadioButtons()

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

    /**
     * Updates the settings and the radio buttons accordingly when the fragment is resumed.
     */
    override fun onResume() {
        super.onResume()
        updateRadioButtons()
    }


    /**
     * Updates the settings with the radio buttons and returns it.
     */
    private fun updateSettings() {

        settings = Settings( binding.kdpRadio.isChecked, binding.keskRadio.isChecked, binding.kokRadio.isChecked,
            binding.liikRadio.isChecked, binding.psRadio.isChecked, binding.rRadio.isChecked, binding.sdRadio.isChecked,
            binding.vasRadio.isChecked, binding.vihrRadio.isChecked )

        memberListViewModel.let {
            applicationScope.launch {
                memberListViewModel.updateSettings(settings)
            }
        }
    }

    /**
     * Updates the radiobuttons.
     */
    private fun updateRadioButtons() {
        memberListViewModel.let {
            applicationScope.launch {
                settings = memberListViewModel.getSettings() as Settings
            }
        }

        binding.kdpRadio.isChecked = settings.showKDP
        binding.keskRadio.isChecked = settings.showKesk
        binding.kokRadio.isChecked = settings.showKok
        binding.liikRadio.isChecked = settings.showLiik
        binding.psRadio.isChecked = settings.showPS
        binding.rRadio.isChecked = settings.showRKP
        binding.sdRadio.isChecked = settings.showSDP
        binding.vasRadio.isChecked = settings.showVas
        binding.vihrRadio.isChecked = settings.showVihr

    }
}