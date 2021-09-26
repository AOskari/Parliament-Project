package com.example.parliamentproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.adapters.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.data.*
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.view_models.MemberListViewModel
import com.example.parliamentproject.data.view_models.MemberListViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberListBinding

/**
 * A Fragment subclass, which displays all found members of parliament in a RecyclerView.
 */
class MemberListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter : MemberListAdapter
    private lateinit var settings : Settings
    private lateinit var memberListViewModel : MemberListViewModel
    private lateinit var memberListViewModelFactory: MemberListViewModelFactory

    private var chosenParties = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Getting or creating the MemberListViewModel instance depending if there is one or not.
        memberListViewModelFactory = MemberListViewModelFactory((activity?.application as MPApplication).memberRepository,
            (activity?.application as MPApplication).settingsRepository)
        memberListViewModel = ViewModelProvider(this, memberListViewModelFactory).get(MemberListViewModel::class.java)

        adapter = MemberListAdapter()

        // Setting the adapter and layoutManager to the RecyclerView, as well the query functions for the SearchView.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.searchview.setOnQueryTextListener(this)

        // Setting an onClickListener which opens the settings DialogFragment.
        binding.settingsButton.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        // Applying an observer to the settings to get the latest updates to the settings.
        memberListViewModel.getSettings().observe(viewLifecycleOwner, { s ->
            s.let {
                settings = it
                chosenParties = settings.chosenParties()
                Log.d("MemberListFragment", "Settings changed. Updating it. $chosenParties")
            }
        })

        return binding.root
    }

    /** Handles the SearchView input. If there is no input, the RecyclerView will be empty.
     * @onQueryTextSubmit
     * @onQueryTextChange
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

    /** Searches the database according to the SearchView's input field and the settings. */
    private fun getMembers(query: String) {
        Log.d("getMembers in MemberList", "Current settings $settings")
        val searchQuery = "%$query%"
        memberListViewModel.getMembers(searchQuery, chosenParties, settings.minAge, settings.maxAge).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }
}