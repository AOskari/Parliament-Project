package com.example.parliamentproject.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.data.repositories.ReviewRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/** A subclass of ViewModel used in the MemberReviewFragment. */
class ReviewViewModel(private val reviewRepository: ReviewRepository, val member: Member) : ViewModel() {

    var rating = 0

    var star1Active = false
    var star2Active = false
    var star3Active = false
    var star4Active = false
    var star5Active = false

    val applicationScope = CoroutineScope(SupervisorJob())

    /** Adds the created Review into the Database. */
    suspend fun addReview(review: Review) = reviewRepository.addReview(review)


    /** Functions for toggling the starActive Boolean variables. */
    fun toggleStar1Active() {
        star1Active = !star1Active
    }
    fun toggleStar2Active() {
        star2Active = !star2Active
    }
    fun toggleStar3Active() {
        star3Active = !star3Active
    }
    fun toggleStar4Active() {
        star4Active = !star4Active
    }
    fun toggleStar5Active() {
        star5Active = !star5Active
    }
}

/** Used for creating a ReviewVielModel, which has the variables saved in it.*/
class ReviewViewModelFactory(private val repository: ReviewRepository, val member: Member) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository, member) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}