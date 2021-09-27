package com.example.parliamentproject.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentproject.R
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.databinding.CustomReviewRowBinding

/**RecyclerView.Adapter subclass which is utilized in the RecyclerView of MemberReviewFragment.
 * Initially it displays only the title of a Review, but it can be expanded to show the comment and rating of the review, by
 * clicking on the title. */
class ReviewListAdapter : RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>() {

    private lateinit var binding : CustomReviewRowBinding
    private var reviewList : List<Review> = emptyList()

    /** Called everytime a new row is created. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ReviewListViewHolder {
        binding = CustomReviewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewListViewHolder(binding)
    }

    /** Called when scrolling the RecyclerView and when the user expands or collapses a row. */
    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {

        binding.review = reviewList[position]
        val isExpanded = reviewList[position].expanded

        binding.expendableLayout.visibility = if (isExpanded) VISIBLE else GONE
        Log.d("Expanded visibility", "${binding.expendableLayout.visibility} at row $position")

        // Setting a onClickListener to the title of the row, which expands or collapses whenever it is clicked.
        binding.rowTitle.setOnClickListener {
            var expandable = reviewList[position]
            expandable.expanded = !expandable.expanded
            notifyItemChanged(position)
        }

        setStars(reviewList[position])
    }

    /** Returns the size of the reviewList. */
    override fun getItemCount() = reviewList.size

    /** Returns the current position of the row. */
    override fun getItemViewType(position: Int) = position

    /** Displays the rating as stars in the MemberFragment's RecyclerView. */
    private fun setStars(review: Review) {
        if (review.rating >= 1) binding.star1.setImageResource(R.drawable.star_filled)
        if (review.rating >= 2) binding.star2.setImageResource(R.drawable.star_filled)
        if (review.rating >= 3) binding.star3.setImageResource(R.drawable.star_filled)
        if (review.rating >= 4) binding.star4.setImageResource(R.drawable.star_filled)
        if (review.rating >= 5) binding.star5.setImageResource(R.drawable.star_filled)
    }

    /** Updates the reviewList with new data. */
    fun setData(reviews: List<Review>) {
        reviewList = reviews
        notifyDataSetChanged()
    }

    /** Defines the content of each row on the RecycleView. */
    class ReviewListViewHolder(binding: CustomReviewRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.rowTitle
        val comment: TextView = binding.commentView
    }

}