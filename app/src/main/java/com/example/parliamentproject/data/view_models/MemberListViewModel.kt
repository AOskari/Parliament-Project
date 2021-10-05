package com.example.parliamentproject.data.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.repositories.MemberRepository
import com.example.parliamentproject.data.repositories.SettingsRepository

/** A subclass of ViewModel. Provides the necessary data to the MemberListFragment. */
class MemberListViewModel(private val memberRepository: MemberRepository, private val settingsRepository: SettingsRepository): ViewModel() {

    var settings = Settings()

    private val _membersList = MutableLiveData<List<Member>>()
    val membersList : LiveData<List<Member>>
        get() = _membersList

    private val _foundMembersAmount = MutableLiveData<String>()
    val foundMembersAmount : LiveData<String>
        get() = _foundMembersAmount

    /** Calls the getMembers function of the MemberRepository.
     * @param: The SearchView input. */
    fun getMembers(param: String) = memberRepository.getMembers(param, settings.chosenParties(), settings.minAge, settings.maxAge)

    /** Returns the current settings from the Room Database. */
    fun getSettings() = settingsRepository.getSettings()

    /** Updates the Settings variable. */
    fun updateSettings(s: Settings?) {
        settings = s ?: Settings()
    }

    /** Updates the _membersList MutableLiveData with the given list of Member objects. */
    fun updateMembersList(list: List<Member>) {
        _membersList.value = list
        updateFoundMembersAmount()
    }

    private fun updateFoundMembersAmount() {
        if (_membersList.value != null && _membersList.value?.size != 1) {
            _foundMembersAmount.value = "${_membersList.value?.size} ${if (_membersList.value?.size == 1) "Member" else "Members"} found"
        } else {
            _foundMembersAmount.value = "0 Members found"
        }
    }

    init {
        updateMembersList(emptyList())
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
