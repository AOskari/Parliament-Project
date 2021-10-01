package com.example.parliamentproject.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.data.repositories.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/** A ViewModel subclass used for storing data used in the SettingsFragment. */
class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    var settings = Settings()
    val applicationScope = CoroutineScope(SupervisorJob())

    /** Returns the settings fetched from the Room Database. */
    fun getSettings() = repository.getSettings()

    /** Updates the settings with the given parameter in the Room Database.
     *  @settings: the modified Settings object. */
    fun updateSettings(settings: Settings) = repository.updateSettings(settings)

    /** Updates the Settings variable. */
    fun updateSettingsInViewModel(s: Settings?) {
        settings = s ?: Settings()
    }
}

/** Used for creating an instance of the SettingsViewModel, or fetching an already created instance of it. */
class SettingsViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}