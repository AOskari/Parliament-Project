package com.example.parliamentproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.parliamentproject.databinding.ActivityMainBinding
import com.example.parliamentproject.workers.DatabaseUpdateWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentContainer: FragmentContainerView
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navSetup()
        enableWorkManager()
    }


    /**
     * Sets up the navigation components for the MainActivity.
     */
    private fun navSetup() {
        val navFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navFragment.navController
        fragmentContainer = binding.fragmentContainerView
        bottomNav = binding.bottomNav
        bottomNav.setupWithNavController(navController)
    }


    /**
     * Enables a WorkManager, which updates the Room database once a day.
     */
    private fun enableWorkManager() {
        val workRequest = PeriodicWorkRequest.Builder(DatabaseUpdateWorker::class.java, 1, TimeUnit.DAYS).build()
            WorkManager.getInstance(applicationContext)
            .enqueue(workRequest)
    }

}