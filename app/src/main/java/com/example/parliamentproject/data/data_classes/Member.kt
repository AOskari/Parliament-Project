package com.example.parliamentproject.data.data_classes

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * The data class used for storing information of Members of Parliament into the Room Database.
 * As the information is retrieved initially in JSON, each variable contains a @Json annotation so that Moshi is able to parse it.
 */
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

    /** Displays the full name of the Member. This function is mainly used for data binding. */
    fun displayName() = "$first $last"

    /** Displays the status information of the Member. This function is mainly used in the RecyclerView in MemberListFragment. */
    fun statusInfo() = "Age: ${Calendar.getInstance().get(Calendar.YEAR) - bornYear}${if (minister) ", minister" else ""}"

    /** Returns the official abbreviation of the party. */
    fun displayPartyName() : String {
        return when (party) {
            "sd" -> "SDP"
            "r" -> "RKP"
            "kd" -> "KD"
            "kok" -> "Kok."
            "liik" -> "Liik."
            "ps" -> "PS"
            "kesk" -> "Kesk."
            "vihr" -> "Vihr."
            "vas" -> "Vas."
            else -> ""
        }
    }
}