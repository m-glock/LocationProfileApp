package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.ProfileDetailAction
import com.mglock.locationprofileapp.database.entities.Timeframe

data class ProfileWithRelations (
    @Embedded
    val profile: Profile,

    @Relation(parentColumn = "profile_uid", entityColumn = "place_uid")
    val place: Place?,

    @Relation(parentColumn = "profile_uid", entityColumn = "timeframe_uid")
    val timeframe: List<Timeframe>,

    @Relation(
        parentColumn = "profile_uid",
        entityColumn = "detail_action_uid",
        associateBy = Junction(ProfileDetailAction::class)
    )
    val actions: List<DetailAction>
)