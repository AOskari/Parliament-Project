package com.example.parliamentproject.data.repositories

import com.example.parliamentproject.data.dao.SettingsDao
import com.example.parliamentproject.data.data_classes.Settings

/** A repository containing necessary functions for accessing the settings_table. */
class SettingsRepository(private val settingsDao: SettingsDao) {

    /** Returns the current settings. */
    fun getSettings() = settingsDao.getSettings()

    /** Update the current settings with the given parameter.
     * @settings: A reference to the modified settings. */
    fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings)
}