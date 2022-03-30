package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "detail_action")
data class DetailAction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="detail_action_uid")
    val detailActionUID: Long,

    val title: String,

    var active: Boolean
): Serializable