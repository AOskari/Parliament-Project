package com.example.parliamentproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.R
import com.example.parliamentproject.adapters.ReviewListAdapter
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.view_models.MemberViewModel
import com.example.parliamentproject.data.view_models.MemberViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberBinding
import com.example.parliamentproject.network.MembersApi

/** A Fragment subclass which displays the data of the chosen Member of Parliament. */
class MemberFragment : Fragment() {

    private lateinit var binding : FragmentMemberBinding
    private lateinit var adapter : ReviewListAdapter
    private lateinit var memberViewModel : MemberViewModel
    private lateinit var memberViewModelFactory: MemberViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Setting up the binding and adapter for the RecyclerView.
        adapter = ReviewListAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member, container, false)
        binding.memberReviews.adapter = adapter
        binding.memberReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.memberReviews.setHasFixedSize(true)

        val sharedPrefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        // Getting or creating the MemberViewModel instance depending if there is one or not.
        memberViewModelFactory = MemberViewModelFactory((activity?.application as MPApplication).reviewRepository,
            MemberFragmentArgs.fromBundle(requireArguments()).member, sharedPrefs)
        memberViewModel = ViewModelProvider(this, memberViewModelFactory).get(MemberViewModel::class.java)

        // Applying a reference of the memberViewModel to the binding.
        binding.memberViewModel = memberViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Gets the image of the chosen MP or gets it from the cache depending if it is cached already.
        MembersApi.setMemberImage(memberViewModel.member.picture, binding.memberPicture, this)

        // ClickListeners for opening the MemberReviewFragment and expanding the RecyclerView containing Review objects.
        binding.addReview.setOnClickListener { openReviewFragment() }
        binding.reviewToggle.setOnClickListener { toggleReviewsDisplay() }

        // Setting an observer to the LiveData list of Reviews for updating the reviews.
        memberViewModel.getReviewsByPersonNumber(memberViewModel.member.personNumber).observe(viewLifecycleOwner, { list ->
            list.let {
                adapter.setData(list)
            }
        })

        return binding.root
    }

    /** Opens the MemberReviewFragment with the currently selected Member. */
    private fun openReviewFragment() {
        val action = MemberFragmentDirections.actionMemberFragmentToMemberReviewFragment(memberViewModel.member)
        findNavController().navigate(action)
    }

    /** Changes the guideline percentage for expanding the RecyclerView containing the reviews. */
    private fun toggleReviewsDisplay() {
        memberViewModel.toggleShowReviews()
        if (memberViewModel.showReviews) {
            binding.guideline6.setGuidelinePercent(0.15f)
            binding.memberPicture.visibility = GONE
            binding.memberName.visibility = GONE
            binding.partyName.visibility = GONE
            binding.twitterLogo.visibility = GONE
            binding.twitterLink.visibility = GONE
            binding.miscBar.visibility = GONE
            binding.addReview.visibility = GONE
        }
        else {
            binding.guideline6.setGuidelinePercent(0.6f)
            binding.memberPicture.visibility = VISIBLE
            binding.memberName.visibility = VISIBLE
            binding.partyName.visibility = VISIBLE
            binding.twitterLogo.visibility = VISIBLE
            binding.twitterLink.visibility = VISIBLE
            binding.miscBar.visibility = VISIBLE
            binding.addReview.visibility = VISIBLE
        }
    }
}
