package com.example.parliamentproject.fragments

import MemberOfParliament
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.data.*
import com.example.parliamentproject.databinding.FragmentMemberListBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class MemberListFragment : Fragment() {

    private lateinit var binding: FragmentMemberListBinding

    private val memberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory((activity?.application as MPApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        val adapter = MemberListAdapter()

        // Setting the adapter and layoutManager to the RecyclerView
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)


        // Setting an observer to the MemberViewModel instance.
        memberViewModel.readAllData.observe(viewLifecycleOwner) { member ->
            member.let { adapter.setData(member) }
        }

        return binding.root
    }



    // Will be used for inserting member data to the database from the retrieved list.
    private fun insertDataToDatabase(list: List<Member>) {
        for (i in 1 until list.size) {
            val member = list[i]
            memberViewModel.addMember(list[i])
            println("Added ${member.first} + ${member.last} to the database")
        }
    }

}