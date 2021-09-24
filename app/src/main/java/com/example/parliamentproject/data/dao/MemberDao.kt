package com.example.parliamentproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.parliamentproject.data.data_classes.Member

/**
 * A data access object interface, which describes each Query to the database.
 */
@Dao
interface MemberDao {

    /**
     * Adds a Member to the member_table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMember(member: Member)

    /**
     * Adds a comment to the selected member.
     */
  /*  @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comment)
*/
    /**
     * Returns all Member objects from the member_table in an ascending order.
     */
    @Query("SELECT * FROM member_table ORDER BY first ASC")
    fun readAllData(): LiveData<List<Member>>

    /**
     * Selects data from the member_Table filtered by the parameter.
     */
    @Query("SELECT * FROM member_table WHERE (first LIKE :param OR last LIKE :param) AND party IN (:parties) AND age >= :minAge AND age <= :maxAge")
    fun getMembers(param: String, parties: List<String>, minAge: Int, maxAge: Int): LiveData<List<Member>>

    /**
     * Fetches all the Comments which are associated with the chosen Member (personNumber).
     */
   /* @Transaction
    @Query("SELECT * FROM member_table WHERE personNumber = :personNumber")
    suspend fun getMemberWithComments(personNumber: Int) : List<MemberWithComments> */

    /**
     * Updates the member_table.
     */
    @Update
    fun updateMembers(members: List<Member>)
}