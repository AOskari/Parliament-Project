package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.Guideline
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.R
import com.example.parliamentproject.adapters.ReviewListAdapter
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.view_models.MemberViewModel
import com.example.parliamentproject.data.view_models.MemberViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberBinding
import com.example.parliamentproject.network.MembersApi

/** A Fragment subclass which displays the data of the chosen Member of Parliament. */
class MemberFragment : Fragment() {

    private lateinit var binding : FragmentMemberBinding
    private lateinit var member: Member
    private lateinit var adapter : ReviewListAdapter
    private lateinit var memberViewModel : MemberViewModel
    private lateinit var memberViewModelFactory: MemberViewModelFactory

    private var showReviews = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Getting or creating the MemberViewModel instance depending if there is one or not.
        memberViewModelFactory = MemberViewModelFactory((activity?.application as MPApplication).reviewRepository,
            MemberFragmentArgs.fromBundle(requireArguments()).member)
        memberViewModel = ViewModelProvider(this, memberViewModelFactory).get(MemberViewModel::class.java)

        // Setting up the binding and adapter for the RecyclerView.
        adapter = ReviewListAdapter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member, container, false)
        binding.memberReviews.adapter = adapter
        binding.memberReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.memberReviews.setHasFixedSize(true)

        // Setting the UI properties
        setUIProperties()

        // Setting an observer to the LiveData list of Reviews for updating the reviews.
        memberViewModel.getReviewsByPersonNumber(member.personNumber).observe(viewLifecycleOwner, { list ->
            list.let {
                adapter.setData(list)
            }
        })
        return binding.root
    }

    /** Sets up the UI using the saved properties in the MemberViewModel object. */
    private fun setUIProperties() {

        member = memberViewModel.member

        // Gets the image of the chosen MP and caches it.
        MembersApi.setMemberImage(member.picture, binding.memberPicture, this)

        binding.memberName.text = "${member.displayName()} ${member.personNumber}"
        binding.partyName.text = member.party
        binding.ministerInfo2.text = if (member.minister) "Yes" else "No"
        binding.seatInfo2.text = member.seatNumber.toString()
        binding.ageInfo2.text = member.age.toString()
        binding.twitterLink.text = if (member.twitter != "") member.twitter else "No Twitter"

        binding.addReview.setOnClickListener { openReviewFragment() }
        binding.reviewToggle.setOnClickListener { toggleReviewsDisplay() }

        when (member.party) {
            "kd" -> setPartyName("Suomen Kristillisdemokraatit")
            "kesk" -> setPartyName("Suomen Keskusta")
            "kok" -> setPartyName("Kansallinen Kokoomus")
            "liik" -> setPartyName("Liike Nyt")
            "ps" -> setPartyName("Perussuomalaiset")
            "r" -> setPartyName("Suomen ruotsalainen kansanpuolue")
            "sd" -> setPartyName("Suomen Sosialidemokraattinen Puolue")
            "vas" -> setPartyName("Vasemmistoliitto")
            "vihr" -> setPartyName("Vihreä liitto")
        }
    }


    /** Sets the party name.
     * @name the name of the Member's party. */
    private fun setPartyName(name: String) { binding.partyName.text = name }

    /** Opens the MemberReviewFragment with the currently selected Member. */
    private fun openReviewFragment() {
        val action = MemberFragmentDirections.actionMemberFragmentToMemberReviewFragment(member)
        findNavController().navigate(action)
    }

    /** Changes the guideline percentage for expanding the RecyclerView containing the reviews. */
    private fun toggleReviewsDisplay() {
        showReviews = !showReviews
        val guideline : Guideline = binding.guideline6
        val toggleButton = binding.reviewToggle
        val image = binding.memberPicture
        val memberName = binding.memberName
        val memberParty = binding.partyName
        val twitterImg = binding.twitterLogo
        val twitterUrl = binding.twitterLink
        val miscBar = binding.miscBar

        if (showReviews) {
            guideline.setGuidelinePercent(0.15f)
            toggleButton.text = "Hide reviews"
            image.visibility = GONE
            memberName.visibility = GONE
            memberParty.visibility = GONE
            twitterImg.visibility = GONE
            twitterUrl.visibility = GONE
            miscBar.visibility = GONE
        }
        else {
            guideline.setGuidelinePercent(0.6f)
            toggleButton.text = "Expand reviews"
            image.visibility = VISIBLE
            memberName.visibility = VISIBLE
            memberParty.visibility = VISIBLE
            twitterImg.visibility = VISIBLE
            twitterUrl.visibility = VISIBLE
            miscBar.visibility = VISIBLE
        }
    }
}
