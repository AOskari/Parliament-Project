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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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

/** A Fragment subclass used for the creation of new Review objects. */
class MemberReviewFragment : Fragment() {

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

        val args: MemberReviewFragmentArgs by navArgs()
        member = args.member

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_review, container, false)
        binding.saveReview.setOnClickListener { saveReview() }

        setRatingButtonListeners()

        return binding.root
    }

    /** Saves the EditText content and the ToggleButton content in to the Database.
     * Goes back to the MemberFragment after saving the content. */
    private fun saveReview() {

        rating = when {
            star5Active -> 5
            star4Active -> 4
            star4Active -> 3
            star4Active -> 2
            star4Active -> 1
            else -> 0
        }

        val review = Review(member.personNumber, binding.reviewTitle.text.toString(), binding.commentField.text.toString(), rating)

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

        val action = MemberReviewFragmentDirections.actionMemberReviewFragmentToMemberFragment(member)
        findNavController().navigate(action)
    }


    /** Controls which stars are filled depending on the star that is clicked. */
    private fun setRatingButtonListeners() {

        binding.rating1.setOnClickListener {
            star1Active = !star1Active
            if (star1Active) setStarsActive(1)
            else setStarsInactive(5)
            Log.d("Rating btn clicked", "1 status: $star1Active")
        }

        binding.rating2.setOnClickListener {
            star2Active = !star2Active
            if (star2Active) setStarsActive(2)
            else setStarsInactive(4)
            Log.d("Rating btn clicked", "2 status: $star2Active")
        }

        binding.rating3.setOnClickListener {
            star3Active = !star3Active
            if (star3Active) setStarsActive(3)
            else setStarsInactive(3)
            Log.d("Rating btn clicked", "3 status: $star3Active")
        }

        binding.rating4.setOnClickListener {
            star4Active = !star4Active
            if (star4Active) setStarsActive(4)
            else setStarsInactive(2)
            Log.d("Rating btn clicked", "4 status: $star4Active")
        }

        binding.rating5.setOnClickListener {
            star5Active = !star5Active
            if (star5Active) setStarsActive(5)
            else setStarsInactive(1)
            Log.d("Rating btn clicked", "5 status: $star5Active")
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