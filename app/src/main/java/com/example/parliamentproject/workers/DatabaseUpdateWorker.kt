package com.example.parliamentproject.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.parliamentproject.data.MemberViewModel
import com.example.parliamentproject.network.MembersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A Worker subclass, which updates the member_table of the Room Database periodically.
 */
class DatabaseUpdateWorker(context: Context, workerParameters: WorkerParameters,
                           private val memberViewModel: MemberViewModel) : Worker(context, workerParameters) {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun doWork(): Result {
        updateDatabase()
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("Worker", "Worker has stopped.")
    }

    fun updateDatabase() {
        // Update database using MemberViewModel.

        let {
            applicationScope.launch {
                memberViewModel.updateMembers()
            }
        }

    }


}