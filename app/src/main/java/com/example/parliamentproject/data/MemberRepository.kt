package com.example.parliamentproject.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * A Repository class, to handle data operations.
 */
class MemberRepository(private val memberDao: MemberDao) {

    // Calling the readAllData function from the memberDao instance,
    // to get a LiveData object containing a list of Member objects.
    val readAllData: LiveData<List<Member>> = memberDao.readAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }
}