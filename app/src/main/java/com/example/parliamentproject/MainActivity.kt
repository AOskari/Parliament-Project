package com.example.parliamentproject

import ParliamentMembersData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.parliamentproject.databinding.ActivityMainBinding
import com.example.parliamentproject.fragments.MemberInfoFragment
import com.example.parliamentproject.workers.DatabaseUpdateWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var memberInfoFragment: MemberInfoFragment
    private lateinit var bottomNav : BottomNavigationView

    private lateinit var workManager: WorkManager
    private lateinit var workRequest: WorkRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navFragment.navController
        memberInfoFragment = MemberInfoFragment()
        fragmentContainer = binding.fragmentContainerView
        bottomNav = binding.bottomNav


        workRequest = PeriodicWorkRequest.Builder(DatabaseUpdateWorker::class.java, 15, TimeUnit.HOURS).build()

        WorkManager.getInstance(applicationContext)
            .enqueue(workRequest)


        bottomNav.setupWithNavController(navController)
    }

}