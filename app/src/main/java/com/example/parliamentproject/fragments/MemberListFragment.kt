package com.example.parliamentproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.data.*
import com.example.parliamentproject.databinding.FragmentMemberListBinding
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance

/**
 * A Fragment subclass, which displays all found members of parliament in a RecyclerView.
 */
class MemberListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter : MemberListAdapter

    private lateinit var settings : Settings

    // Contains the parties selected in settings. Gets updated onResume.
    private var chosenParties = listOf<String>()

    /**
     * Search settings. These are controlled by togglebuttons.
     */

    private var ageRange = 1..100

    private val memberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory((activity?.application as MPApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = MemberListAdapter()
        initializeSettings()

        // Setting the adapter and layoutManager to the RecyclerView
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        // Setting an onClickListener which opens the settings DialogFragment.
        binding.settingsButton.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToSettingsFragment(settings)
            findNavController().navigate(action)
        }


        val searchview = binding.searchview
        searchview.setOnQueryTextListener(this) // This = The declared onQueryText functions below

        return binding.root
    }

    /**
     * Updates the settings when the Fragment is resumed.
     */
    override fun onResume() {
        super.onResume()
        updateSettings()
    }

    /**
     * @onQueryTextSubmit
     * @onQueryTextChange
     * Handles the searchview input. If there is no input, the RecyclerView will be empty.
     */
    override fun onQueryTextSubmit(query: String): Boolean {
        if (query != null && (query != "")) getByParameter(query)
        else adapter.setData(emptyList())
        return true
    }
    override fun onQueryTextChange(query: String): Boolean {
        if (query != null && (query != "")) getByParameter(query)
        else adapter.setData(emptyList())
        return true
    }

    /**
     * Searches the database according to the SearchView's input field and the settings.
     */
    private fun getByParameter(query: String) {
        val searchQuery = "%$query%"
        memberViewModel.getMembers(searchQuery, chosenParties).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    private fun setAgeRange(min: Int, max: Int) {
        ageRange = min..max
    }

    /**
     * Attempts to update the settings with arguments.
     */
    private fun updateSettings() {
        try {

            val args: MemberListFragmentArgs by navArgs()
            settings = args.settings
            Log.d("Success", "Arguments found, updated settings")
        } catch (e: Exception) {

            settings = Settings()
            Log.d("Exception", e.message.toString())
        }
        chosenParties = settings.settingsAsList()
    }

    private fun initializeSettings() {
        // Add fetching settings from database later
        settings = Settings()
    }
}