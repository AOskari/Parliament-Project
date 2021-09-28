package com.example.parliamentproject.adapters

import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.databinding.MemberlistRowBinding
import com.example.parliamentproject.fragments.MemberListFragmentDirections


/** RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberListFragment.
 * It displays members of parliament in a clear and stylish list, containing their first and last name,
 * age, party and minister status. */
class MemberListAdapter: RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    private lateinit var binding : MemberlistRowBinding
    private var memberList : List<Member> = emptyList()

    /** Called once everytime a new row is created on the RecyclerView. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        binding = MemberlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("MemberListAdapter onCreateViewHolder", "Called. memberList: $memberList")
        return MemberListViewHolder(binding)
    }

    /** Updates the rows when scrolling the RecyclerView. */
    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        binding.member = memberList[position]
        Log.d("MemberListAdapter onBindViewHolder", "Called. current member: ${binding.member}")
    }

    /** Returns the size of the current list. */
    override fun getItemCount() = memberList.size

    /** Returns the position of the row. */
    // Currently this prevents the RecyclerView from updating the data
    // when making new queries with the SearchView.
    override fun getItemViewType(position: Int) = position

    /** Updates the data in the memberList variable. */
    fun setData(members: List<Member>) {
        memberList = members
        notifyDataSetChanged()


        Log.d("MemberListAdapter setData", "List parameter: $memberList")
    }

    /** Defines the content of each row on the RecycleView. */
    class MemberListViewHolder(binding: MemberlistRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val partyText: TextView = binding.partyAbbreviation
        val memberName: TextView = binding.memberName
        val memberStatusInfo: TextView = binding.memberStatusInfo

        val infoButton = binding.memberInfoButton.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToMemberFragment(binding.member as Member)
            Navigation.findNavController(itemView).navigate(action)
        }
    }

}