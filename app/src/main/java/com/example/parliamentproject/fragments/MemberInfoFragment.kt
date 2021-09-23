package com.example.parliamentproject.fragments

import MemberOfParliament
import ParliamentMembersData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.databinding.FragmentMemberInfoBinding
import java.util.*


class MemberInfoFragment : Fragment() {

    private lateinit var binding: FragmentMemberInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_info, container, false)

        val args: MemberReviewFragmentArgs by navArgs()

        changeMember(args.member)

        return binding.root
    }


    fun changeMember(member: Member) {

        var rndNumber = ParliamentMembersData.members.indices.random()
        val rndMemb : MemberOfParliament = ParliamentMembersData.members[rndNumber]

        binding.constituency.text = "${member.constituency}"
        binding.age.text = "Age: ${Calendar.getInstance().get(Calendar.YEAR) - rndMemb.bornYear}"

        when (member.party) {
            "kd" -> binding.partyImage.setImageResource(R.drawable.kdp)
            "kesk" -> binding.partyImage.setImageResource(R.drawable.kesk)
            "kok" -> binding.partyImage.setImageResource(R.drawable.kok)
            "liik" -> binding.partyImage.setImageResource(R.drawable.liik)
            "ps" -> binding.partyImage.setImageResource(R.drawable.ps)
            "r" -> binding.partyImage.setImageResource(R.drawable.rkp)
            "sd" -> binding.partyImage.setImageResource(R.drawable.sdp)
            "vas" -> binding.partyImage.setImageResource(R.drawable.vas)
            "vihr" -> binding.partyImage.setImageResource(R.drawable.vihr)
        }

    }
}