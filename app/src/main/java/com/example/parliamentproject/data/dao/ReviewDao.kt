package com.example.parliamentproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parliamentproject.data.data_classes.Review

/**
 * A Data Access Object for adding and getting Comment objects from the Database.
 */
@Dao
interface ReviewDao {

    /**
     * Adds a comment to the Database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReview(review: Review)

    /**
     * Returns all comments from the Database which belong to the selected member.
     */
    @Query("SELECT * FROM review_table WHERE personNumber = :personNumber")
    fun getReviewsByPersonNumber(personNumber: Int): LiveData<List<Review>>

}