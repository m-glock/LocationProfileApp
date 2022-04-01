package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.Timeframe
import java.io.Serializable

data class ProfileWithRelations (
    @Embedded
    val profile: Profile,

    @Relation(parentColumn = "profile_uid", entityColumn = "place_uid")
    var place: Place?,

    @Relation(parentColumn = "profile_uid", entityColumn = "timeframe_uid")
    var timeframe: Timeframe?,

    @Relation(
        parentColumn = "profile_uid",
        entityColumn = "detail_action_uid"
    )
    val actions: List<DetailAction>
): Serializable