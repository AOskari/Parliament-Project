package com.example.parliamentproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.R
import com.example.parliamentproject.adapters.MemberListAdapter
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.view_models.MemberListViewModel
import com.example.parliamentproject.data.view_models.MemberListViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberListBinding

/** A Fragment subclass, which displays all found members of parliament in a RecyclerView. */
class MemberListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMemberListBinding
    private lateinit var adapter : MemberListAdapter
    private lateinit var memberListViewModel : MemberListViewModel
    private lateinit var memberListViewModelFactory: MemberListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = MemberListAdapter()

        // Setting the adapter and layoutManager to the RecyclerView, as well the query functions for the SearchView.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.searchview.setOnQueryTextListener(this)

        // Getting or creating the MemberListViewModel instance depending if there is one or not.
        memberListViewModelFactory = MemberListViewModelFactory((activity?.application as MPApplication).memberRepository,
            (activity?.application as MPApplication).settingsRepository)
        memberListViewModel = ViewModelProvider(this, memberListViewModelFactory).get(MemberListViewModel::class.java)

        binding.memberListViewModel = memberListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Enables the SearchView to be activated by clicking it anywhere.
        binding.searchview.setOnClickListener { binding.searchview.isIconified = false }

        // Setting an onClickListener which opens the settings DialogFragment.
        binding.settingsButton.setOnClickListener {

            // Hiding the soft keyboard.
            val kb = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            kb.hideSoftInputFromWindow(view?.windowToken, 0)

            val action = MemberListFragmentDirections.actionMemberListFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        // Applying an observer to the settings to get the latest updates to the settings.
        memberListViewModel.getSettings().observe(viewLifecycleOwner, { s ->
            s.let {
                memberListViewModel.updateSettings(it)
                Log.d("MemberListFragment", "Updating settings: ${memberListViewModel.settings.chosenParties()}")
            }
        })

        return binding.root
    }

    /** Handles the SearchView input. If there is no input, the RecyclerView will be empty.
     * @onQueryTextSubmit
     * @onQueryTextChange */
    override fun onQueryTextSubmit(query: String): Boolean {
        if (query != "") getMembers(query)
        else {
            memberListViewModel.updateMembersList(emptyList())
            adapter.setData(emptyList())
        }
        return true
    }
    override fun onQueryTextChange(query: String): Boolean {
        if (query != "") getMembers(query)
        else {
            memberListViewModel.updateMembersList(emptyList())
            adapter.setData(emptyList())
        }
        return true
    }


    /** Searches the database according to the SearchView's input field and the settings. */
    private fun getMembers(query: String) {

        val searchQuery = "%$query%"

        memberListViewModel.getMembers(searchQuery).observe(this, { list ->
            list.let {
                memberListViewModel.updateMembersList(it)
                adapter.setData(memberListViewModel.membersList.value ?: emptyList())
                Log.d("observer update called", it.toString())
            }
        })
    }
}
