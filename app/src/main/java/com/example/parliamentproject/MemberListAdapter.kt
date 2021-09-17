package com.example.parliamentproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.data.Member
import java.util.*
import java.util.Calendar.YEAR


/**
 * RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberListFragment.
 * It displays the found members of parliament in a clear and stylish list, containing their first and last name,
 * age, party and minister status.
 */
class MemberListAdapter: RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    private var memberList : List<Member> = emptyList<Member>()

    /**
     * Called everytime a new row is created on the RecyclerView, and
     * is responsible for the creation of the RecyclerView rows.
      */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {

        // Getting the View object from the parent ViewGroup, in this case MainActivity
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)

        return MemberListViewHolder(view)
    }


    /**
     * Called constantly when scrolling or updating the RecyclerView with new data, and
     *  is essentially used for setting the correct data into the RecycleView rows.
     */
    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        val currentItem = memberList[position]

      //  holder.image.setImageResource(currentItem.picture) // TODO: Add image of party logic
        holder.memberName.text = "${currentItem.first} ${currentItem.last}"
        holder.memberStatusInfo.text = "Age: ${Calendar.getInstance().get(YEAR) - currentItem.bornYear}, " +
                "${currentItem.party}${if (currentItem.minister) ", minister" else ""}"
    }

    /**
     * Returns the size of the current list.
     */
    override fun getItemCount() = memberList.size


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
    class MemberListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val memberName: TextView = view.findViewById(R.id.member_name)
        val memberStatusInfo: TextView = view.findViewById(R.id.member_statusInfo)
    }

}