package com.example.parliamentproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.parliamentproject.data.data_classes.Member

/** A Data Access Object for accessing the member_table in the Room Database. */
@Dao
interface MemberDao {

    /** Adds a Member to the member_table. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMember(member: Member)

    /** Returns all Member objects from the member_table in an ascending order. */
    @Query("SELECT * FROM member_table ORDER BY first ASC")
    fun readAllData(): LiveData<List<Member>>

    /** Selects data from the member_Table filtered by the parameter. */
    @Query("SELECT * FROM member_table WHERE (first || ' ' || last) LIKE :param AND party IN (:parties) AND age >= :minAge AND age <= :maxAge")
    fun getMembers(param: String, parties: List<String>, minAge: Int, maxAge: Int): LiveData<List<Member>>

    /** Updates the member_table. */
    @Update
    fun updateMembers(members: List<Member>)
}