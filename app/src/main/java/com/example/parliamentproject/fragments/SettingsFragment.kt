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
import com.example.parliamentproject.R
import com.example.parliamentproject.databinding.FragmentSettingsBinding

class SettingsFragment : DialogFragment() {

    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.settingsBack.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToMemberListFragment(
                chosenParties() )
            findNavController().navigate(action)
            dismiss()
        }

        // Setting the background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    private fun chosenParties(): Array<String> {

        val list = mutableListOf<String>()

        if (binding.kdpRadio.isChecked) list.add("kdp")
        if (binding.keskRadio.isChecked) list.add("kesk")
        if (binding.kokRadio.isChecked) list.add("kok")
        if (binding.liikRadio.isChecked) list.add("liik")
        if (binding.psRadio.isChecked) list.add("ps")
        if (binding.rRadio.isChecked) list.add("rkp")
        if (binding.sdRadio.isChecked) list.add("sdp")
        if (binding.vasRadio.isChecked) list.add("vas")
        if (binding.vihrRadio.isChecked) list.add("vihr")

        return list.toTypedArray()
    }
}