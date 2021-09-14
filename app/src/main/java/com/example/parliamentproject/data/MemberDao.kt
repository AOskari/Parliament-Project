package com.example.parliamentproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMember(member: Member)

    @Query("SELECT * FROM member_table ORDER BY first ASC")
    fun readAllData(): LiveData<List<Member>>
    
    @Query("DELETE FROM member_table")
    fun deleteAll()
}