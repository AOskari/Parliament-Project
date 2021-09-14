package com.example.parliamentproject.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Appendable

/**
 * A subclass of ViewModel, for providing the correct data to the UI.
 */
class MemberViewModel(private val repository: MemberRepository): ViewModel() {

    // Getting the LiveData object from the MemberRepository
    val readAllData: LiveData<List<Member>> = repository.readAllData

    /**
     * Calls the addMember function of the MemberRepository.
     */
    fun addMember(member: Member) = viewModelScope.launch {
        // Using a coroutine to avoid blocking the main thread.
        repository.addMember(member)
    }
}

class MemberViewModelFactory(private val repository: MemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}