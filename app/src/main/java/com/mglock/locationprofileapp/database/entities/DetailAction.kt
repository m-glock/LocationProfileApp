package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import java.io.Serializable

@Entity(tableName = "detail_action")
data class DetailAction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="detail_action_uid")
    val detailActionUID: Long,

    @ColumnInfo(name="profile_uid")
    val profileUID: Long?,

    val title: DetailActionOption,

    @ColumnInfo(name="detail_action_value")
    val detailActionValue: String
): Serializable {
    override fun toString(): String {
        return "${title.title}: $detailActionValue"
    }
}