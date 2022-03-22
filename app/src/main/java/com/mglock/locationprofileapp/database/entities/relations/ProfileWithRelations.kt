package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.ProfileDetailAction

data class ProfileWithRelations (
    @Embedded
    val profile: Profile,

    @Relation(parentColumn = "profile_uid", entityColumn = "place_uid")
    val place: Place?,

    //TODO
    //@Relation(parentColumn = )
    //val timeframe: TimeframeWithRelations,

    @Relation(
        parentColumn = "profile_uid",
        entityColumn = "detail_action_uid",
        associateBy = Junction(ProfileDetailAction::class)
    )
    val actions: List<DetailAction>
)