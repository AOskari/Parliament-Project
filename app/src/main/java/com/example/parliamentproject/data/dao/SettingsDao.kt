package com.example.parliamentproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.parliamentproject.data.data_classes.Settings

/** A Data Access Object for accessing the settings_table in the Room Database. */
@Dao
interface SettingsDao {

    /** Gets the settings current settings. */
    @Query("SELECT * FROM settings_table")
    fun getSettings(): LiveData<Settings>

    /** Updates the settings with the given data */
    @Update
    fun updateSettings(settings: Settings)

    /** Used for initializing settings.*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSettings(settings: Settings)
}