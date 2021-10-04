package com.example.parliamentproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.databinding.MemberlistRowBinding
import com.example.parliamentproject.fragments.MemberListFragmentDirections


/** RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberListFragment.
 * It displays members of parliament in a clear and stylish list, containing their first and last name,
 * age, party and minister status. */
class MemberListAdapter: RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    private var memberList : List<Member> = emptyList()

    /** Called once everytime a new row is created on the RecyclerView. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding = MemberlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    /** Updates the rows when scrolling the RecyclerView. */
    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {

        holder.binding.member = memberList[position]
        holder.binding.memberInfoButton.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToMemberFragment(holder.binding.member as Member)
            holder.itemView.findNavController().navigate(action)
        }
    }

    /** Returns the size of the current list. */
    override fun getItemCount() = memberList.size

    /** Returns the position of the row. */
    override fun getItemViewType(position: Int) = position

    /** Updates the data in the memberList variable. */
    fun setData(members: List<Member>) {
        this.memberList = members
        notifyDataSetChanged()
    }

    /** Defines the content of each row on the RecycleView. */
    class MemberListViewHolder(val binding: MemberlistRowBinding) : RecyclerView.ViewHolder(binding.root)

}