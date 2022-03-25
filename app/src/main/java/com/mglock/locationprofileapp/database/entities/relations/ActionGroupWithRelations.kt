package com.mglock.locationprofileapp.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mglock.locationprofileapp.database.entities.ActionGroup
import com.mglock.locationprofileapp.database.entities.DetailAction

class ActionGroupWithRelations (
    @Embedded
    val actionGroup: ActionGroup,

    @Relation(parentColumn = "action_group_uid", entityColumn = "detail_action_uid")
    val detailActions: List<DetailAction>
)