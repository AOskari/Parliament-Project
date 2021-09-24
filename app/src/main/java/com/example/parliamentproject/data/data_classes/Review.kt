package com.example.parliamentproject.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
data class Review(
    val personNumber : Int,
    val title: String = "",
    val comment : String = "",
    val rating : Int = 0
) {
    @PrimaryKey(autoGenerate = true) var i = 0
    var expanded : Boolean = false // Used for creating a expandable RecyclerView row.
}