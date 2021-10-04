package com.example.parliamentproject.data.view_models

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.repositories.SettingsRepository
import com.google.gson.Gson

/** A ViewModel subclass which contains the necessary data displayed in the MainFragment. */
class MainViewModel(private val settingsRepository: SettingsRepository, private val sharedPrefs: SharedPreferences?) : ViewModel() {

    private val _settings = MutableLiveData<Settings>()
    val settings : LiveData<Settings>
        get() = _settings

    private val _lastMember = MutableLiveData<Member>()
    val lastMember : LiveData<Member>
        get() = _lastMember

    private val _chosenSettingsText = MutableLiveData<String>()
    val chosenSettingsText : LiveData<String>
        get() = _chosenSettingsText

    private val _lastUpdatedText = MutableLiveData<String>()
    val lastUpdatedText : LiveData<String>
        get() = _lastUpdatedText

    /** Gets the current settings from the Room Database.*/
    fun settingsFromRepository() = settingsRepository.getSettings()

    /** Updates the Settings variable. */
    fun updateSettings(s: Settings?) {
        _settings.value = s ?: Settings()
        Log.d("updateSettings", "${_settings.value}")
        setChosenPartiesText()
    }

    init { getUpdatesFromPrefs() }

    /** Gets the recently viewed Member object from sharedPreferences. */
    fun getUpdatesFromPrefs() {
        val gson = Gson()
        val json = sharedPrefs?.getString("recentlyViewedMember", "")
        _lastMember.value = gson.fromJson(json, Member::class.java)
        _lastUpdatedText.value = sharedPrefs?.getString("dbLastUpdated", "")
    }

    /** Returns the current settings in a wanted style. */
    private fun setChosenPartiesText() {
        var partyText = "Parties displayed:"

        for (i in 0 until (_settings.value?.chosenParties()?.size?.minus(1) ?: 0)) {
            partyText += "\n${_settings.value?.chosenParties()?.get(i) ?: 0}"
        }
        partyText += "\nAge range:\n${_settings.value?.minAge} - ${_settings.value?.maxAge}"
        _chosenSettingsText.value = partyText
    }
}

/** Used for creating or fetching an instance of the MainListViewModel. */
class MainViewModelFactory(private val settingsRepository: SettingsRepository, private val sharedPrefs: SharedPreferences?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(settingsRepository, sharedPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
