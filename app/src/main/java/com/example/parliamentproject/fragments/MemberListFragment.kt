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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.data.*
import com.example.parliamentproject.databinding.FragmentMemberListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A Fragment subclass, which displays all found members of parliament in a RecyclerView.
 */
class MemberListFragment : Fragment(), SearchView.OnQueryTextListener {

    // TODO: Add Settings to the database and implement update logic for it.

    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter : MemberListAdapter
    private lateinit var settings : Settings

    // Contains the parties selected in settings. Gets updated onResume.
    private var chosenParties = listOf<String>()
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val memberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory((activity?.application as MPApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = MemberListAdapter()
        updateSettings()

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
        Log.d("onResume", "MemberListFragment onResume called")
    }

    /**
     * @onQueryTextSubmit
     * @onQueryTextChange
     * Handles the SearchView input. If there is no input, the RecyclerView will be empty.
     */
    override fun onQueryTextSubmit(query: String): Boolean {
        if (query != null && (query != "")) getMembers(query)
        else adapter.setData(emptyList())
        return true
    }
    override fun onQueryTextChange(query: String): Boolean {
        if (query != null && (query != "")) getMembers(query)
        else adapter.setData(emptyList())
        return true
    }

    /**
     * Searches the database according to the SearchView's input field and the settings.
     */
    private fun getMembers(query: String) {
        val searchQuery = "%$query%"
        memberViewModel.getMembers(searchQuery, chosenParties, settings.minAge, settings.maxAge).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }


    /**
     * Fetches the Settings data from the Room Database.
     */
    private fun updateSettings() {

        try {

            let {
                applicationScope.launch {
                    settings = memberViewModel.getSettings() as Settings
                }
            }
            chosenParties = settings.settingsAsList()

            Log.d("Success", "Fetching setting data was succesful. Updating settings.")
        } catch (e: Exception) {
            Log.d("Exception", "${ e.message.toString() }. Settings not updated.")
        }
    }
}