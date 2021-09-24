package com.example.parliamentproject.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.data.repositories.ReviewRepository

/**
 * A subclass of ViewModel used in the MemberReviewFragment.
 */
class ReviewViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {

    suspend fun addReview(review: Review) = reviewRepository.addReview(review)

}

/**
 * Used for creating a ReviewVielModel, which has the variables saved in it.
 */
class ReviewViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}