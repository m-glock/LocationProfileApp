package com.mglock.locationprofileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import java.io.Serializable

@Entity(
    tableName = "detail_action",
    foreignKeys = [ForeignKey(
        entity = Profile::class,
        parentColumns = ["profile_uid"],
        childColumns = ["profile_id"],
        onUpdate = CASCADE
    )]
)
data class DetailAction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="detail_action_uid")
    val detailActionUID: Long,

    @ColumnInfo(name="profile_id")
    val profileId: Long?,

    val title: DetailActionOption,

    @ColumnInfo(name="detail_action_value")
    val detailActionValue: String,

    val mode: String?
): Serializable {
    override fun toString(): String {
        val modeText = valueToString()
        return "${title.title}: $modeText"
    }

    fun valueToString(): String{
        return if(mode != null) "$mode to $detailActionValue" else detailActionValue
    }
}