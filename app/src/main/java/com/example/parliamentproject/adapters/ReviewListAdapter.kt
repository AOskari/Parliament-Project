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

class ReviewListAdapter : RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>() {

    private lateinit var binding : CustomReviewRowBinding
    private var reviewList : List<Review> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ReviewListViewHolder {
        binding = CustomReviewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)



        return ReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {

        binding.review = reviewList[position]
        val isExpanded = reviewList[position].expanded

        binding.expendableLayout.visibility = if (isExpanded) VISIBLE else GONE
        Log.d("Expanded visibility", "${binding.expendableLayout.visibility} at row $position")

        binding.rowTitle.setOnClickListener {
            var expandable = reviewList[position]
            expandable.expanded = !expandable.expanded
            notifyItemChanged(position)
        }

        setStars(reviewList[position])
    }

    override fun getItemCount() = reviewList.size

    override fun getItemViewType(position: Int) = position


    fun setData(reviews: List<Review>) {
        reviewList = reviews
        notifyDataSetChanged()
    }

    /**
     * Displays the rating as stars in the MemberFragment's RecyclerView.
     */
    fun setStars(review: Review) {

        if (review.rating >= 1) binding.star1.setImageResource(R.drawable.star_filled)
        if (review.rating >= 2) binding.star2.setImageResource(R.drawable.star_filled)
        if (review.rating >= 3) binding.star3.setImageResource(R.drawable.star_filled)
        if (review.rating >= 4) binding.star4.setImageResource(R.drawable.star_filled)
        if (review.rating >= 5) binding.star5.setImageResource(R.drawable.star_filled)

    }


    class ReviewListViewHolder(binding: CustomReviewRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.rowTitle
        val comment: TextView = binding.commentView

        val star1: ImageView = binding.star1
        val star2: ImageView = binding.star2
        val star3: ImageView = binding.star3
        val star4: ImageView = binding.star4
        val star5: ImageView = binding.star5

    }

}