package com.example.parliamentproject.data.repositories

import androidx.lifecycle.LiveData
import com.example.parliamentproject.data.dao.MemberDao
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.network.MembersApi

/**
 * A Repository class, to handle data operations.
 */
class MemberRepository(private val memberDao: MemberDao) {

    // Calling the readAllData function from the memberDao instance,
    // to get a LiveData object containing a list of Member objects.
    val readAllData: LiveData<List<Member>> = memberDao.readAllData()

    fun getMembers(param: String, parties: List<String>,  minAge: Int, maxAge: Int) = memberDao.getMembers(param, parties, minAge, maxAge)

    suspend fun addMember(member: Member) = memberDao.addMember(member)

    suspend fun updateMembers() {
        memberDao.updateMembers(getMembersFromApi())
    }

    private suspend fun getMembersFromApi() = MembersApi.retrofitService.getProperties()
}