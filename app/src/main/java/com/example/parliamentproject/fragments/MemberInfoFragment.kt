package com.example.parliamentproject.fragments

import MemberOfParliament
import ParliamentMembersData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.parliamentproject.R
import com.example.parliamentproject.databinding.FragmentMemberInfoBinding
import java.util.*


class MemberInfoFragment : Fragment() {

    private lateinit var binding: FragmentMemberInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_info, container, false)
        binding.changeMember.setOnClickListener { changeMember() }

        changeMember()

        return binding.root
    }


    fun changeMember() {

        var rndNumber = ParliamentMembersData.members.indices.random()
        val rndMemb : MemberOfParliament = ParliamentMembersData.members[rndNumber]

        binding.memberName.text = "${rndMemb.first} ${rndMemb.last}"
        binding.constituency.text = "${rndMemb.constituency}"
        binding.age.text = "${Calendar.getInstance().get(Calendar.YEAR) - rndMemb.bornYear} years old"

        when (rndMemb.party) {
            "kdp" -> binding.partyImage.setImageResource(R.drawable.kdp)
            "kesk" -> binding.partyImage.setImageResource(R.drawable.kesk)
            "kok" -> binding.partyImage.setImageResource(R.drawable.kok)
            "liik" -> binding.partyImage.setImageResource(R.drawable.liik)
            "ps" -> binding.partyImage.setImageResource(R.drawable.ps)
            "rkp" -> binding.partyImage.setImageResource(R.drawable.rkp)
            "sdp" -> binding.partyImage.setImageResource(R.drawable.sdp)
            "vas" -> binding.partyImage.setImageResource(R.drawable.vas)
            "vihr" -> binding.partyImage.setImageResource(R.drawable.vihr)
        }

    }
}