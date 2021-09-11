package com.example.parliamentproject.fragments

import MemberOfParliament
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.MemberListAdapter
import com.example.parliamentproject.R
import com.example.parliamentproject.databinding.FragmentMemberListBinding


class MemberListFragment : Fragment() {

    private lateinit var binding: FragmentMemberListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // TODO: Fetch data from MP-API, and cache it for the duration of the usage of the app.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val testList = generateTestList(300) // For RecyclerView test purposes only

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        // Setting the adapter and layoutManager to the RecyclerView
        binding.recyclerView.adapter = MemberListAdapter(testList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }


    // TEST FUNCTION
    private fun generateTestList(size: Int): List<MemberOfParliament> {
        val memberList = ArrayList<MemberOfParliament>()

        for (i in 0 until size) {
            val member = MemberOfParliament(i, i, "Subject $i", "Test", "Party $i", false)
            memberList += member
        }
        return memberList
    }

}