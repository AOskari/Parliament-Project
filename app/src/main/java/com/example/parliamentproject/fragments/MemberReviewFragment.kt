package com.example.parliamentproject.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.data.view_models.ReviewViewModel
import com.example.parliamentproject.data.view_models.ReviewViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberReviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MemberReviewFragment : DialogFragment() {

    private lateinit var binding : FragmentMemberReviewBinding
    private lateinit var member : Member
    private val applicationScope = CoroutineScope(SupervisorJob())
    private var rating = 0

    private var star1Active = false
    private var star2Active = false
    private var star3Active = false
    private var star4Active = false
    private var star5Active = false


    private val reviewViewModel : ReviewViewModel by viewModels {
        ReviewViewModelFactory((activity?.application as MPApplication).reviewRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_review, container, false)

        val args: MemberReviewFragmentArgs by navArgs()
        member = args.member

        binding.saveReview.setOnClickListener { saveReview() }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setRatingButtonListeners()

        return binding.root
    }

    /**
     * Saves the EditText content and the ToggleButton content in to the Database.
     * Closes the fragment after saving the content.
     */
    private fun saveReview() {

        rating = when {
            star5Active -> 5
            star4Active -> 4
            star4Active -> 3
            star4Active -> 2
            star4Active -> 1
            else -> 0
        }

        val review = Review(member.personNumber, "Placeholder title", binding.commentField.text.toString(), rating)

        try {
            let {
                applicationScope.launch {
                    reviewViewModel.addReview(review)
                    Log.d("MemberReviewFragment", "Launched coroutine for addReview.")
                }
            }
            Toast.makeText(context, "Review saved.", Toast.LENGTH_SHORT).show()
            Log.d("MemberReviewFragment", "Saving review succesful.")
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to save review.", Toast.LENGTH_SHORT).show()
            Log.d("MemberReviewFragment", "Saving review failed.")
        }

        // Close the fragment.
    }


    /**
     * Controls which stars are filled depending on the star that is clicked.
     */
    private fun setRatingButtonListeners() {

        binding.rating1.setOnClickListener {
            star1Active = !star1Active
            if (star1Active) setStarsInactive(5)
            else setStarsActive(1)
        }

        binding.rating2.setOnClickListener {
            star2Active= !star2Active
            if (star2Active) setStarsInactive(4)
            else setStarsActive(2)
        }

        binding.rating3.setOnClickListener {
            star3Active = !star3Active
            if (star3Active) setStarsInactive(3)
            else setStarsActive(3)
        }

        binding.rating4.setOnClickListener {
            star4Active = !star4Active
            if (star4Active) setStarsInactive(2)
            else setStarsActive(4)
        }

        binding.rating5.setOnClickListener {
            star5Active = !star5Active
            if (star5Active) setStarsInactive(1)
            else setStarsActive(5)
        }

    }

    /**
     * Sets the amount of stars active depending on the parameter.
     */
    private fun setStarsActive(amount: Int) {

        if (amount >= 1) {
            binding.rating1.setImageResource(R.drawable.star_filled)
            star1Active = true
        }
        if (amount >= 2) {
            binding.rating2.setImageResource(R.drawable.star_filled)
            star2Active = true
        }
        if (amount >= 3) {
            binding.rating3.setImageResource(R.drawable.star_filled)
            star3Active = true
        }
        if (amount >= 4) {
            binding.rating4.setImageResource(R.drawable.star_filled)
            star4Active = true
        }
        if (amount >= 5) {
            binding.rating5.setImageResource(R.drawable.star_filled)
            star5Active = true
        }
    }

    /**
     * Sets the amount of stars inactive depending on the parameter.
     */
    private fun setStarsInactive(amount: Int) {
        if (amount >= 1) {
            binding.rating5.setImageResource(R.drawable.star_empty)
            star5Active = false
        }
        if (amount >= 2) {
            binding.rating4.setImageResource(R.drawable.star_empty)
            star4Active = false
        }
        if (amount >= 3) {
            binding.rating3.setImageResource(R.drawable.star_empty)
            star3Active = false
        }
        if (amount >= 4) {
            binding.rating2.setImageResource(R.drawable.star_empty)
            star2Active = false
        }
        if (amount >= 5) {
            binding.rating1.setImageResource(R.drawable.star_empty)
            star1Active = false
        }
    }


}