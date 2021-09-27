package com.example.parliamentproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.databinding.CustomRowBinding
import com.example.parliamentproject.fragments.MemberListFragmentDirections


/** RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberListFragment.
 * It displays members of parliament in a clear and stylish list, containing their first and last name,
 * age, party and minister status. */
class MemberListAdapter: RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    private lateinit var binding : CustomRowBinding
    private var memberList : List<Member> = emptyList()

    /** Called once everytime a new row is created on the RecyclerView. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    /** Updates the rows when scrolling the RecyclerView. */
    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        binding.member = memberList[position]
    }

    /** Returns the size of the current list. */
    override fun getItemCount() = memberList.size

    /** Returns the position of the row. */
    override fun getItemViewType(position: Int) = position

    /** Updates the data in the memberList variable. */
    fun setData(members: List<Member>) {
        memberList = members
        notifyDataSetChanged()
    }

    /** Defines the content of each row on the RecycleView. */
    class MemberListViewHolder(binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.imageView
        val memberName: TextView = binding.memberName
        val memberStatusInfo: TextView = binding.memberStatusInfo

        val asd = binding.memberInfoButton.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToMemberFragment(binding.member as Member)
            Navigation.findNavController(itemView).navigate(action)
        }
    }

}