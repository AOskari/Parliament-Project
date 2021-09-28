package com.example.parliamentproject.data.view_models

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.repositories.SettingsRepository

class MainViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    /** Gets the current settings from the Room Database.*/
    fun getSettings() = settingsRepository.getSettings()
}

/** Used for creating or fetching an instance of the MainListViewModel. */
class MainViewModelFactory(private val settingsRepository: SettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
