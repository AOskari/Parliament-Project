package com.example.parliamentproject.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.repositories.ReviewRepository


class MemberViewModel(private val repository: ReviewRepository) : ViewModel() {

    fun getReviewsByPersonNumber(personNumber: Int) = repository.getReviewsByPersonNumber(personNumber)

}


class MemberViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}