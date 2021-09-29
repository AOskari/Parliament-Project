package com.example.parliamentproject.workers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.parliamentproject.MainActivity
import com.example.parliamentproject.data.MPApplication
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * A Worker subclass, which updates the member_table of the Room Database periodically.
 */
class DatabaseUpdateWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val mpApplication = context.applicationContext as MPApplication
    private val repo = mpApplication.memberRepository
    private val sharedPrefs : SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    /**
     * Called in intervals according to the set TimeUnit on the WorkManager.
     */
    override fun doWork(): Result {
        return try {
            updateDatabase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    /**
     * Used for logging whenever the Worker has stopped.
     */
    override fun onStopped() {
        super.onStopped()
        Log.d("Worker", "Worker has stopped.")
    }

    /** Updates the database by calling the MemberRepository's updateMembers function with a coroutine. */
    private fun updateDatabase() {
        let {
            applicationScope.launch {
                repo.updateMembers()
            }
        }

        // Update the dbLastUpdated key value pair in SharedPreferences
        val prefsEditor = sharedPrefs.edit()
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        prefsEditor.putString("dbLastUpdated", "$date\n$time")
        prefsEditor.commit()
    }
}