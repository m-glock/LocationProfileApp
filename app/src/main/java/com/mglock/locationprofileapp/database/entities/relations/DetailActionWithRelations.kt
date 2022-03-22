package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.ActionGroup
import com.mglock.locationprofileapp.database.entities.DetailAction

data class DetailActionWithRelations (
    @Embedded
    val detailAction: DetailAction,

    @Relation(parentColumn = "detail_action_uid", entityColumn = "action_group_uid")
    val actionGroup: ActionGroup
)