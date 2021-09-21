package com.example.parliamentproject.data.data_classes

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "member_table")
data class Member(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "personNumber") var personNumber: Int,
    @Json(name = "seatNumber") var seatNumber: Int = 0,
    @Json(name = "last") var last: String,
    @Json(name = "first") var first: String,
    @Json(name = "party") var party: String,
    @Json(name = "minister") var minister: Boolean = false,
    @Json(name = "picture") var picture: String = "",
    @Json(name = "twitter") var twitter: String = "",
    @Json(name = "bornYear") var bornYear: Int = 0,
    @Json(name = "constituency") var constituency: String = "",
    var age : Int? = Calendar.getInstance().get(Calendar.YEAR) - bornYear
) : Parcelable {

    fun displayName() = "$first $last"
    fun statusInfo() = "Age: ${Calendar.getInstance().get(Calendar.YEAR) - bornYear}, " +
            "${party}${if (minister) ", minister" else ""}"
}