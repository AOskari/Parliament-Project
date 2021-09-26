package com.example.parliamentproject.data.repositories

import com.example.parliamentproject.data.dao.ReviewDao
import com.example.parliamentproject.data.data_classes.Review

/** A repository containing necessary functions for accessing the reviews_table. */
class ReviewRepository(private val reviewDao: ReviewDao) {

    /** Returns the reviews given to the selected member.
     * @personNumber: The selected Member's personNumber variable. */
    fun getReviewsByPersonNumber(personNumber: Int) = reviewDao.getReviewsByPersonNumber(personNumber)

    /** Adds the given Review object to the reviews_table.
     * @review: The Review object that was created on the MemberReviewFragment. */
    suspend fun addReview(review: Review) = reviewDao.addReview(review)
}