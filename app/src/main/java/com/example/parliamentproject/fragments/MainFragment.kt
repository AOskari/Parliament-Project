package com.example.parliamentproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.view_models.MainViewModel
import com.example.parliamentproject.data.view_models.MainViewModelFactory
import com.example.parliamentproject.databinding.FragmentMainBinding
import com.example.parliamentproject.network.MembersApi

/** A Fragment subclass which displays a summary of the usage of the app. */
class MainFragment : Fragment() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        // Setting up the binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        // Getting or creating the MainViewModel instance depending if there is one or not.
        mainViewModelFactory = MainViewModelFactory((activity?.application as MPApplication).settingsRepository, sharedPrefs)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        // Setting the MainViewModel object to the binding.
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Setting an observer for checking updates for Settings.
        mainViewModel.settingsFromRepository().observe(viewLifecycleOwner, { s ->
            s.let {
                mainViewModel.updateSettings(it)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setRecentlyViewedMemberImage()
        mainViewModel.getUpdatesFromPrefs()
    }

    /** Fetches the recently viewed Member from SharedPreferences and the cached picture of the Member.*/
    private fun setRecentlyViewedMemberImage() {
        try {
            MembersApi.setMemberImage(mainViewModel.lastMember.value?.picture ?: "", binding.mainMemberImage, this)
            Log.d("MainFragment", "Success retrieving recently viewed member.")
        } catch (e: Exception) {
            Log.d("MainFragment", "Failed to fetch recently viewed member. ${e.message}")
        }
    }
}