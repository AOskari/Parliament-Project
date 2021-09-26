package com.example.parliamentproject.data.repositories

import androidx.lifecycle.LiveData
import com.example.parliamentproject.data.dao.MemberDao
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.network.MembersApi

/** A repository containing necessary functions for accessing the member_table. */
class MemberRepository(private val memberDao: MemberDao) {

    /** Gets all the members filtered by the given SearchView input and current settings. */
    fun getMembers(param: String, parties: List<String>,  minAge: Int, maxAge: Int) = memberDao.getMembers(param, parties, minAge, maxAge)

    /** Updates the member_table with the newly fetched JSON data. This is used mainly by the DatabaseUdateWorker. */
    suspend fun updateMembers() = memberDao.updateMembers(getMembersFromApi())

    /** Fetches the Member of Parliament JSON data from the API. */
    private suspend fun getMembersFromApi() = MembersApi.retrofitService.getProperties()
}