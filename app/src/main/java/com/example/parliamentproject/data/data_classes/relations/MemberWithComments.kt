package com.example.parliamentproject.data.data_classes.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.parliamentproject.data.data_classes.Comment

/**
 * Used for querying all the comments that are associated with the selected Member.
 */
data class MemberWithComments(
 /*   @Embedded val comment: Comment,
    @Relation(
        parentColumn = "personNumber",
        entityColumn = "member"
    ) */

    //val comments: List<Comment>
    val test: String
)