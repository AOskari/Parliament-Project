package com.example.parliamentproject.data.repositories

import com.example.parliamentproject.data.dao.SettingsDao
import com.example.parliamentproject.data.data_classes.Settings

class SettingsRepository(private val settingsDao: SettingsDao) {


    fun getSettings() = settingsDao.getSettings()

    suspend fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings)

}