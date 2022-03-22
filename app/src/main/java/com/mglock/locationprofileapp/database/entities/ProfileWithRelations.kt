package com.mglock.locationprofileapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithRelations (
    @Embedded
    val profile: Profile,

    @Relation(parentColumn = "profile_uid", entityColumn = "place_uid")
    val place: Place?,

    //TODO add actions and timeframe
)