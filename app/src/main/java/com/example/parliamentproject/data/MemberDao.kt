package com.example.parliamentproject.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * A data access object interface, which describes each Query to the database.
 */
@Dao
interface MemberDao {

    /**
     * Adds a Member to the member_table.
     */
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
    @Query("SELECT * FROM member_table WHERE (first LIKE :param OR last LIKE :param) AND party IN (:parties) AND age >= :minAge AND age <= :maxAge")
    fun getMembers(param: String, parties: List<String>, minAge: Int, maxAge: Int): LiveData<List<Member>>



    /**
     * Gets the settings current settings.
     */
    @Query("SELECT * FROM settings_table")
    fun getSettings(): Settings

    /**
     * Updates the settings with the given data
     */
    @Update
    fun updateSettings(settings: Settings)

    /**
     * Used for initializing settings.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSettings(settings: Settings)

}