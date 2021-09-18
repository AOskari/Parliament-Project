package com.example.parliamentproject.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.data.Settings
import com.example.parliamentproject.databinding.FragmentSettingsBinding
import java.lang.Exception

class SettingsFragment : DialogFragment() {

    private lateinit var binding : FragmentSettingsBinding
    private lateinit var settings : Settings

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.settingsBack.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToMemberListFragment(
                updateSettings() )
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

        try {
            val args: MemberListFragmentArgs by navArgs()
            settings = args.settings

        } catch (e: Exception) {
            settings = Settings()
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


    /**
     * Updates the settings with the radio buttons and returns it.
     */
    private fun updateSettings(): Settings {

        settings = Settings( binding.kdpRadio.isChecked, binding.keskRadio.isChecked, binding.kokRadio.isChecked,
            binding.liikRadio.isChecked, binding.psRadio.isChecked, binding.rRadio.isChecked, binding.sdRadio.isChecked,
            binding.vasRadio.isChecked, binding.vihrRadio.isChecked )

        return settings
    }
}