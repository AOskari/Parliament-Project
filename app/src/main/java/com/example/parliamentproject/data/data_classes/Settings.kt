package com.example.parliamentproject.data.data_classes

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/** Stores all the settings data. This is stored in the Room Database. */
@Parcelize
@Entity(tableName = "settings_table")
data class Settings (
    @PrimaryKey(autoGenerate = true)
    var showKDP: Boolean = true,
    var showKesk: Boolean = true,
    var showKok: Boolean = true,
    var showLiik: Boolean = true,
    var showPS: Boolean = true,
    var showRKP: Boolean = true,
    var showSDP: Boolean = true,
    var showVas: Boolean = true,
    var showVihr: Boolean = true,
    var minAge : Int = 18,
    var maxAge : Int = 100
        )  : Parcelable {

    /** Returns settins as a List of Strings. */
    fun chosenParties() : List<String> {
        val list = mutableListOf<String>()

        if (showKDP) list.add("kd")
        if (showKesk) list.add("kesk")
        if (showKok) list.add("kok")
        if (showLiik) list.add("liik")
        if (showPS) list.add("ps")
        if (showRKP) list.add("r")
        if (showSDP) list.add("sd")
        if (showVas) list.add("vas")
        if (showVihr) list.add("vihr")

        return list
    }

}