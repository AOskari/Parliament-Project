package com.example.parliamentproject.data.repositories

import com.example.parliamentproject.data.dao.ReviewDao
import com.example.parliamentproject.data.data_classes.Review

class ReviewRepository(private val reviewDao: ReviewDao) {

    fun getReviewsByPersonNumber(personNumber: Int) = reviewDao.getReviewsByPersonNumber(personNumber)

    suspend fun addReview(review: Review) = reviewDao.addReview(review)
}