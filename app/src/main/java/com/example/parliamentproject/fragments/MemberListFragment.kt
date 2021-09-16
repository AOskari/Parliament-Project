package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.data.*
import com.example.parliamentproject.databinding.FragmentMemberListBinding

/**
 * A Fragment subclass, which displays all found members of parliament in a RecyclerView.
 */
class MemberListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter : MemberListAdapter

    /**
     * Search settings. These are controlled by togglebuttons.
     */
    private var showAll = false
    private var showMen = true
    private var showWomen = true

    private var ageRange = 1..100

    private val memberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory((activity?.application as MPApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MemberListAdapter()


        // Setting the adapter and layoutManager to the RecyclerView
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        // Setting an onClickListener which opens the settings DialogFragment.
        binding.settingsButton.setOnClickListener {
            var settingsFragment = SettingsFragment()
            settingsFragment.show(childFragmentManager, "settingsDialog")
        }


        val searchview = binding.searchview
        searchview.setOnQueryTextListener(this) // This = The declared onQueryText functions below

        return binding.root
    }

    /**
     * @onQueryTextSubmit
     * @onQueryTextChange
     * Handles the searchview input. If there is no input, the RecyclerView will be empty.
     */
    override fun onQueryTextSubmit(query: String): Boolean {
        if (query != null && (query != "" || showAll)) getByParameter(query)
        else adapter.setData(emptyList())
        return true
    }
    override fun onQueryTextChange(query: String): Boolean {
        if (query != null && (query != "" || showAll)) getByParameter(query)
        else adapter.setData(emptyList())
        return true
    }

    /**
     * Searches the database according to the SearchView's input field.
     */
    private fun getByParameter(query: String) {
        val searchQuery = "%$query%"
        memberViewModel.getByParameter(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

    /**
     * Functions for adjusting settings.
     */
    private fun toggleShowAll() = showAll != showAll
    private fun toggleShowMen() = showMen != showMen
    private fun toggleShowWomen() = showWomen != showWomen

    private fun setAgeRange(min: Int, max: Int) {
        ageRange = min..max
    }
}