package com.example.parliamentproject.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.parliamentproject.data.MPApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * A Worker subclass, which updates the member_table of the Room Database periodically.
 */
class DatabaseUpdateWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val mpApplication = context.applicationContext as MPApplication
    private val repo = mpApplication.memberRepository

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

    /**
     * Updates the database by calling the MemberRepository's updateMembers function with a coroutine.
     */
    private fun updateDatabase() {
        let {
            applicationScope.launch {
                repo.updateMembers()
            }
        }

    }


}