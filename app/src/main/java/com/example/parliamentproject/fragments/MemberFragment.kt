package com.example.parliamentproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.parliamentproject.R
import com.example.parliamentproject.databinding.FragmentMemberBinding
import com.example.parliamentproject.network.MembersApi
import java.lang.reflect.Member

/**
 * Displays the data of the chosen Member of Parliament.
 */
class MemberFragment : Fragment() {

    private lateinit var binding : FragmentMemberBinding
    private lateinit var member: Member

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member, container, false)

        setData()

        return binding.root
    }

    /**
     * Sets the correct data in to the View.
     */
    private fun setData() {
        val args: MemberFragmentArgs by navArgs()

        val member = args.member

        // Gets the image of the chosen MP and caches it.
        MembersApi.setMemberImage(member.picture, binding.memberPicture, this)

        binding.memberName.text = "${member.displayName()} ${member.personNumber}"
        binding.partyName.text = member.party
        binding.ministerInfo2.text = if (member.minister) "Yes" else "No"

        binding.seatInfo2.text = member.seatNumber.toString()
        binding.ageInfo2.text = member.age.toString()
        binding.twitterLink.text = if (member.twitter != "") member.twitter else "No Twitter"


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

    private fun setPartyName(name: String) { binding.partyName.text = name }
}