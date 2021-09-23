package com.example.parliamentproject.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity(tableName = "comment_table")
data class Comment(
 /*   @PrimaryKey(autoGenerate = false)
    val member: Member, */
    val comment: String
    )