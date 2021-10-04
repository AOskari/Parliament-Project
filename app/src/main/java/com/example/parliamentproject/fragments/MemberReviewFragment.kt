package com.example.parliamentproject.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.data.view_models.MemberViewModel
import com.example.parliamentproject.data.view_models.MemberViewModelFactory
import com.example.parliamentproject.data.view_models.ReviewViewModel
import com.example.parliamentproject.data.view_models.ReviewViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberReviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** A Fragment subclass used for the creation of new Review objects. */
class MemberReviewFragment : Fragment() {

    private lateinit var reviewViewModel : ReviewViewModel
    private lateinit var reviewViewModelFactory: ReviewViewModelFactory
    private lateinit var binding : FragmentMemberReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Setting up the binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_review, container, false)

        // Getting or creating the ReviewViewModel instance depending if there is one or not.
        reviewViewModelFactory = ReviewViewModelFactory((activity?.application as MPApplication).reviewRepository,
            MemberReviewFragmentArgs.fromBundle(requireArguments()).member)
        reviewViewModel = ViewModelProvider(this, reviewViewModelFactory).get(ReviewViewModel::class.java)

        // Setting onClickListeners to the save button and rating buttons.
        binding.saveReview.setOnClickListener { saveReview() }
        setRatingButtonListeners()

        return binding.root
    }

    /** Saves the EditText content and the ToggleButton content in to the Database.
     * Goes back to the MemberFragment after saving the content. */
    private fun saveReview() {

        reviewViewModel.setRating()

        val review = Review(reviewViewModel.member.personNumber, binding.reviewTitle.text.toString(),
            binding.commentField.text.toString(), reviewViewModel.rating)

        try {
            let {
                reviewViewModel.applicationScope.launch {
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

        val action = MemberReviewFragmentDirections.actionMemberReviewFragmentToMemberFragment(reviewViewModel.member)
        findNavController().navigate(action)
    }


    /** Controls which stars are filled depending on the star that is clicked. */
    private fun setRatingButtonListeners() {

        binding.rating1.setOnClickListener {
            reviewViewModel.toggleStar1Active()
            if (reviewViewModel.star1Active) setStarsActive(1)
            else setStarsInactive(5)
        }

        binding.rating2.setOnClickListener {
            reviewViewModel.toggleStar2Active()
            if (reviewViewModel.star2Active) setStarsActive(2)
            else setStarsInactive(4)
        }

        binding.rating3.setOnClickListener {
            reviewViewModel.toggleStar3Active()
            if (reviewViewModel.star3Active) setStarsActive(3)
            else setStarsInactive(3)
        }

        binding.rating4.setOnClickListener {
            reviewViewModel.toggleStar4Active()
            if (reviewViewModel.star4Active) setStarsActive(4)
            else setStarsInactive(2)
        }

        binding.rating5.setOnClickListener {
            reviewViewModel.toggleStar5Active()
            if (reviewViewModel.star5Active) setStarsActive(5)
            else setStarsInactive(1)
        }

    }

    /** Sets the amount of stars active depending on the parameter. */
    private fun setStarsActive(amount: Int) {

        if (amount >= 1) binding.rating1.setImageResource(R.drawable.star_filled)
        if (amount >= 2) binding.rating2.setImageResource(R.drawable.star_filled)
        if (amount >= 3) binding.rating3.setImageResource(R.drawable.star_filled)
        if (amount >= 4) binding.rating4.setImageResource(R.drawable.star_filled)
        if (amount >= 5) binding.rating5.setImageResource(R.drawable.star_filled)

    }

    /** Sets the amount of stars inactive depending on the parameter. */
    private fun setStarsInactive(amount: Int) {

        if (amount >= 1) binding.rating5.setImageResource(R.drawable.star_empty)
        if (amount >= 2) binding.rating4.setImageResource(R.drawable.star_empty)
        if (amount >= 3) binding.rating3.setImageResource(R.drawable.star_empty)
        if (amount >= 4) binding.rating2.setImageResource(R.drawable.star_empty)
        if (amount >= 5) binding.rating1.setImageResource(R.drawable.star_empty)

    }
}