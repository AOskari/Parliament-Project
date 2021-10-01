package com.example.parliamentproject.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.view_models.*
import com.example.parliamentproject.databinding.FragmentMainBinding
import com.example.parliamentproject.network.MembersApi
import com.google.gson.Gson

/** A Fragment subclass which displays a summary of the usage of the app. */
class MainFragment : Fragment() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Setting up the binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        // Getting or creating the MainViewModel instance depending if there is one or not.
        mainViewModelFactory = MainViewModelFactory((activity?.application as MPApplication).settingsRepository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        setSettingsObserver()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRecentlyViewedMember()
        updateDbLastUpdatedText()
    }

    /** Fetches the recently viewed Member from SharedPreferences and the cached picture of the Member.*/
    private fun setRecentlyViewedMember() {
        try {

            val gson = Gson()
            val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val json = sharedPrefs?.getString("recentlyViewedMember", "")
            val lastMember = gson.fromJson(json, Member::class.java)

            binding.mainMemberName.text = "${lastMember.first} ${lastMember.last}"

            MembersApi.setMemberImage(lastMember.picture, binding.mainMemberImage, this)

            Log.d("MainFragment", "Success retrieving recently viewed member.")
        } catch (e: Exception) {
            Log.d("MainFragment", "Failed to fetch recently viewed member. ${e.message}")
        }
    }

    /** Gets the current settings from the database and displays them on the fragment. */
    private fun setSettingsObserver() {
        mainViewModel.getSettings().observe(viewLifecycleOwner, { s ->
            s.let {
                mainViewModel.updateSettings(it)
                var chosenPartiesText = "Parties displayed:"
                for (i in 0 until mainViewModel.settings.chosenParties().size - 1) {
                    chosenPartiesText += "\n${mainViewModel.settings.chosenParties()[i]}"
                }
                chosenPartiesText += "\nAge range:\n${mainViewModel.settings.minAge} - ${mainViewModel.settings.maxAge}"
                binding.mainCurrentSettings.text = chosenPartiesText
            }
        })
    }

    /** Updates the db updated text in the Fragment. */
    private fun updateDbLastUpdatedText() {
        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val lastUpdated = sharedPrefs?.getString("dbLastUpdated", "")
        binding.mainLastUpdatedText.text = lastUpdated
    }
}