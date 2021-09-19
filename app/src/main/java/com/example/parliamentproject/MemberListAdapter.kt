package com.example.parliamentproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.data.Member
import com.example.parliamentproject.databinding.CustomRowBinding
import com.example.parliamentproject.databinding.FragmentMemberListBinding
import java.util.*
import java.util.Calendar.YEAR


/**
 * RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberListFragment.
 * It displays the found members of parliament in a clear and stylish list, containing their first and last name,
 * age, party and minister status.
 */
class MemberListAdapter: RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    private lateinit var binding : CustomRowBinding
    private var memberList : List<Member> = emptyList()

    /**
     * Called everytime a new row is created on the RecyclerView, and
     * is responsible for the creation of the RecyclerView rows.
      */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    /**
     * Called constantly when scrolling or updating the RecyclerView with new data, and
     *  is essentially used for setting the correct data into the RecycleView rows.
     */
    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        binding.member = memberList[position]
      //  holder.image.setImageResource(currentItem.picture) // TODO: Add image of party logic
    }

    /**
     * Returns the size of the current list.
     */
    override fun getItemCount() = memberList.size

    override fun getItemViewType(position: Int) = position

    /**
     * Updates the data in the memberList variable.
     */
    fun setData(members: List<Member>) {
        memberList = members
        notifyDataSetChanged()
    }


    /**
     * Defines the content of each row on the RecycleView.
     */
    class MemberListViewHolder(binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.imageView
        val memberName: TextView = binding.memberName
        val memberStatusInfo: TextView = binding.memberStatusInfo
    }

}