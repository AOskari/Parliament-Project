package com.example.parliamentproject.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.repositories.ReviewRepository

/** A ViewModel subclass which contains all the required data used in the fragment and certain functions for retrieving data.
 * @repository: A reference to the ReviewRepository
 * @member: The currently selected Member object in the MemberFragment.m*/
class MemberViewModel(private val repository: ReviewRepository, val member: Member) : ViewModel() {

    var showReviews = false

    /** Retrieves Review objects from the Room Database utilizing the ReviewRepository.
     * @personNumber: the personNumber of the Member object. */
    fun getReviewsByPersonNumber(personNumber: Int) = repository.getReviewsByPersonNumber(personNumber)

    fun toggleShowReviews() {
        showReviews = !showReviews
    }
}

/** A Factory method for creating or getting an instance of the MemberViewModel object.
 * @repository: Required ReviewRepository type parameter for the MemberViewModel.
 * @member: Required Member type parameter for the MemberViewModel. */
class MemberViewModelFactory(private val repository: ReviewRepository, private val member: Member) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository, member) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}