package com.example.parliamentproject.data.dao

import androidx.room.*
import com.example.parliamentproject.data.data_classes.Settings

@Dao
interface SettingsDao {

    /**
     * Gets the settings current settings.
     */
    @Query("SELECT * FROM settings_table")
    fun getSettings(): Settings

    /**
     * Updates the settings with the given data
     */
    @Update
    fun updateSettings(settings: Settings)

    /**
     * Used for initializing settings.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSettings(settings: Settings)


}