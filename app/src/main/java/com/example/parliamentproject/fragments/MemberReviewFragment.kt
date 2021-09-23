package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.databinding.FragmentMemberReviewBinding

class MemberReviewFragment : Fragment() {

    private lateinit var binding : FragmentMemberReviewBinding
    private lateinit var member : Member
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


        return binding.root
    }

    /**
     * Saves the EditText content and the ToggleButton content in to the Database.
     * Closes the fragment after saving the content.
     */
    private fun saveReview() {


        // Close the fragment.
    }

}