package com.example.parliamentproject.data.view_models

import androidx.lifecycle.*
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.repositories.MemberRepository
import com.example.parliamentproject.data.repositories.SettingsRepository
import kotlinx.coroutines.launch

/**
 * A subclass of ViewModel, for providing the correct data to the UI.
 */
class MemberListViewModel(private val memberRepository: MemberRepository, private val settingsRepository: SettingsRepository): ViewModel() {

    // Getting the LiveData object from the MemberRepository
    val readAllData: LiveData<List<Member>> = memberRepository.readAllData

    /**
     * Calls the addMember function of the MemberRepository.
     */
    fun addMember(member: Member) = viewModelScope.launch {
        memberRepository.addMember(member)
    }

    /**
     * Calls the getMembers function of the MemberRepository.
     */
    fun getMembers(param: String, parties: List<String>,  minAge: Int, maxAge: Int) = memberRepository.getMembers(param, parties, minAge, maxAge)

    suspend fun updateMembers() = memberRepository.updateMembers()

    fun getSettings() = settingsRepository.getSettings()

    suspend fun updateSettings(settings: Settings) = settingsRepository.updateSettings(settings)

}

class MemberListViewModelFactory(private val repository: MemberRepository,
                                 private val settingsRepository: SettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberListViewModel(repository, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
