package com.example.parliamentproject

import ParliamentMembersData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.parliamentproject.databinding.ActivityMainBinding
import com.example.parliamentproject.fragments.MemberInfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var memberInfoFragment: MemberInfoFragment
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navFragment.navController



        memberInfoFragment = MemberInfoFragment()
        fragmentContainer = binding.fragmentContainerView
        bottomNav = binding.bottomNav

        bottomNav.setupWithNavController(navController)
    }

}