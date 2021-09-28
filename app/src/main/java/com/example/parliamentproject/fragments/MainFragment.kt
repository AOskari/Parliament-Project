package com.example.parliamentproject.fragments

import android.content.Context
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
import com.example.parliamentproject.data.view_models.*
import com.example.parliamentproject.databinding.FragmentMainBinding
import com.google.gson.Gson

/** A Fragment subclass which displays a summary of the usage of the app. */
class MainFragment : Fragment() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var binding : FragmentMainBinding

    private lateinit var recentlyViewedMember : Member

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Getting or creating the MainViewModel instance depending if there is one or not.
        mainViewModelFactory = MainViewModelFactory((activity?.application as MPApplication).settingsRepository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        recentlyViewedMemberFromPrefs()

    }

    // SharedPreferences testing.
    // Image fetching needs to be added.
    private fun recentlyViewedMemberFromPrefs() {
        try {
            val gson = Gson()
            val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val json = sharedPrefs.getString("recentlyViewedMember", "")
            recentlyViewedMember = gson.fromJson(json, Member::class.java)

            binding.mainMemberFirstname.text = recentlyViewedMember.first
            binding.mainMemberLastname.text = recentlyViewedMember.last

            Log.d("MainFragment", "Success retrieving recently viewed member.")
        } catch (e: Exception) {
            Log.d("MainFragment", "Failed to fetch recently viewed member. ${e.message}")
        }
    }
}