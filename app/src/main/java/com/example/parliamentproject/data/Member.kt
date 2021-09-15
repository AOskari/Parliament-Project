package com.example.parliamentproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "member_table")
data class Member(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "personNumber") val personNumber: Int,
    @Json(name = "seatNumber") val seatNumber: Int = 0,
    @Json(name = "last") val last: String,
    @Json(name = "first") val first: String,
    @Json(name = "party") val party: String,
    @Json(name = "minister") val minister: Boolean = false,
    @Json(name = "picture") val picture: String = "",
    @Json(name = "twitter") val twitter: String = "",
    @Json(name = "bornYear") val bornYear: Int = 0,
    @Json(name = "constituency") val constituency: String = ""
)