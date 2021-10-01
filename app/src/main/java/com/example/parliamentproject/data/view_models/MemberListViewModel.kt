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

    var settings = Settings()

    /** Calls the getMembers function of the MemberRepository.
     * @param: The SearchView input. */
    fun getMembers(param: String) = memberRepository.getMembers(param, settings.chosenParties(), settings.minAge, settings.maxAge)

    /** Returns the current settings from the Room Database. */
    fun getSettings() = settingsRepository.getSettings()

    /** Updates the Settings variable. */
    fun updateSettings(s: Settings?) {
        settings = s ?: Settings()
    }
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
