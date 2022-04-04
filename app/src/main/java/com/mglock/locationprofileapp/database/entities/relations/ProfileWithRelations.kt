package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.Timeframe
import java.io.Serializable

data class ProfileWithRelations (
    @Embedded
    val profile: Profile,

    @Relation(parentColumn = "place_id", entityColumn = "place_uid")
    var place: Place?,

    @Relation(parentColumn = "timeframe_id", entityColumn = "timeframe_uid")
    var timeframe: Timeframe?,

    @Relation(
        parentColumn = "profile_uid",
        entityColumn = "profile_id"
    )
    var actions: List<DetailAction>
): Serializable