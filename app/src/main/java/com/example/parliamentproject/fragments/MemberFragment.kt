package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.constraintlayout.widget.Guideline
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentproject.R
import com.example.parliamentproject.adapters.ReviewListAdapter
import com.example.parliamentproject.data.MPApplication
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.view_models.MemberViewModel
import com.example.parliamentproject.data.view_models.MemberViewModelFactory
import com.example.parliamentproject.databinding.FragmentMemberBinding
import com.example.parliamentproject.network.MembersApi
import kotlinx.android.synthetic.main.fragment_member.*

/**
 * Displays the data of the chosen Member of Parliament.
 */
class MemberFragment : Fragment() {

    private lateinit var binding : FragmentMemberBinding
    private lateinit var member: Member
    private lateinit var adapter : ReviewListAdapter
    private var showReviews = false

    private val memberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory((activity?.application as MPApplication).reviewRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = ReviewListAdapter()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member, container, false)
        binding.memberReviews.adapter = adapter
        binding.memberReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.memberReviews.setHasFixedSize(true)

        setProperties()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        memberViewModel.getReviewsByPersonNumber(member.personNumber).observe(this, { list ->
            list.let {
                adapter.setData(list)
            }
        })

    }

    /**
     * Sets the correct data in to the View.
     */
    private fun setProperties() {
        val args: MemberFragmentArgs by navArgs()

        member = args.member

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

    /**
     * @name the name of the Member's party.
     * Sets the party name.
     */
    private fun setPartyName(name: String) { binding.partyName.text = name }

    /**
     * Opens the MemberReviewFragment with the currently selected Member.
     */
    private fun openReviewFragment() {
        val action = MemberFragmentDirections.actionMemberFragmentToMemberReviewFragment(member)
        findNavController().navigate(action)
    }

    /**
     * Changes the guideline percent for displaying the RecyclerView containing the reviews.
     */
    private fun toggleReviewsDisplay() {
        showReviews = !showReviews
        val guideline : Guideline = binding.guideline6
        val toggleButton = binding.reviewToggle
        val reviewList = binding.memberReviews
        val floatingButton = binding.addReview
        val image = binding.memberPicture
        val memberName = binding.memberName
        val memberParty = binding.partyName
        val twitterImg = binding.twitterLogo
        val twitterUrl = binding.twitterLink
        val miscBar = binding.miscBar

        if (showReviews) {
            guideline.setGuidelinePercent(0.15f)
            toggleButton.text = "Hide reviews"
            reviewList.visibility = VISIBLE
            floatingButton.visibility = VISIBLE
            image.visibility = GONE
            memberName.visibility = GONE
            memberParty.visibility = GONE
            twitterImg.visibility = GONE
            twitterUrl.visibility = GONE
            miscBar.visibility = GONE
        }
        else {
            guideline.setGuidelinePercent(0.85f)
            toggleButton.text = "Show and add reviews"
            reviewList.visibility = GONE
            floatingButton.visibility = GONE
            image.visibility = VISIBLE
            memberName.visibility = VISIBLE
            memberParty.visibility = VISIBLE
            twitterImg.visibility = VISIBLE
            twitterUrl.visibility = VISIBLE
            miscBar.visibility = VISIBLE
        }
    }
}
