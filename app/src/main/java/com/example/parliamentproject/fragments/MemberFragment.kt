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

/**
 * Displays the data of the chosen Member of Parliament.
 */
class MemberFragment : Fragment() {

    private lateinit var binding : FragmentMemberBinding

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

        MembersApi.setMemberImage(member.picture, binding.memberPicture, this)

        binding.memberName.text = member.displayName()
    }

}