package com.example.parliamentproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * A data access object interface, which describes each Query to the database.
 */
@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMember(member: Member)

    /**
     * Returns all Member objects from the member_table in an ascending order.
     */
    @Query("SELECT * FROM member_table ORDER BY first ASC")
    fun readAllData(): LiveData<List<Member>>

    /**
     * Selects data from the member_Table filtered by the parameter.
     */
    @Query("SELECT * FROM member_table WHERE (first LIKE :param OR last LIKE :param) AND party IN (:parties)")
    fun getMembers(param: String, parties: List<String>): LiveData<List<Member>>

    /**
     * Deletes all data from the member_Table
     */
    @Query("DELETE FROM member_table")
    fun deleteAll()
}