package com.example.parliamentproject.data

import android.util.Log
import androidx.lifecycle.*
import com.example.parliamentproject.network.MembersApi
import kotlinx.coroutines.launch

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
        repository.addMember(member)
    }

    /**
     * Calls the getMembers function of the MemberRepository.
     */
    fun getMembers(param: String, parties: List<String>,  minAge: Int, maxAge: Int) = repository.getMembers(param, parties, minAge, maxAge)


    fun getSettings() = repository.getSettings()

    suspend fun updateSettings(settings: Settings) = repository.updateSettings(settings)

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
