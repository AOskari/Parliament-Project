package com.example.parliamentproject.data.view_models

import androidx.lifecycle.*
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.repositories.MemberRepository
import com.example.parliamentproject.data.repositories.SettingsRepository
import kotlinx.coroutines.launch
import java.lang.StringBuilder

/** A subclass of ViewModel. Provides the necessary data to the MemberListFragment. */
class MemberListViewModel(private val memberRepository: MemberRepository, private val settingsRepository: SettingsRepository): ViewModel() {

    /** Calls the getMembers function of the MemberRepository.
     * @param: the given SearchView input.
     * @parties: A list of the parties selected in the Settings object.
     * @minAge: The minimum selected age in the Settings.
     * @maxAge: The maximum age selected in the Settings.
     * */
    fun getMembers(param: String, parties: List<String>,  minAge: Int, maxAge: Int) = memberRepository.getMembers(param, parties, minAge, maxAge)

    /** Returns the current settings from the Room Database. */
    fun getSettings() = settingsRepository.getSettings()
}

/** Used for creating or fetching an instance of the MemberListViewModel. */
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
