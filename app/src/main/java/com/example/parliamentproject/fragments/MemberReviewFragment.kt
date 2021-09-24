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

    private val reviewViewModel : ReviewViewModel by viewModels {
        ReviewViewModelFactory((activity?.application as MPApplication).reviewRepository)
    }


    // Create logic for adding comments and rating for the chosen member.
    // Probably a good idea to add the selected member as an argument when moving to this fragment.
    // Display the chosen Member's name on this fragment, to indicate the user who he or she is reviewing.


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

        return binding.root
    }

    /**
     * Saves the EditText content and the ToggleButton content in to the Database.
     * Closes the fragment after saving the content.
     */
    private fun saveReview() {

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

}