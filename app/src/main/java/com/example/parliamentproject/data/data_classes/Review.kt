package com.example.parliamentproject.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey
/** A data class used for defining the rows of review_table in the Room Database. Each row has a personNumber stored in it,
 * which is used for applying the Review object to the correct member. */
@Entity(tableName = "review_table")
data class Review(
    val personNumber : Int,
    val title: String = "",
    val comment : String = "",
    val rating : Int = 0
) {
    @PrimaryKey(autoGenerate = true) var i = 0
    var expanded : Boolean = false // Used for creating an expandable RecyclerView row.
}