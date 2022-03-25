package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.ProfileDetailAction

@Dao
interface ProfileDetailActionDao {
    @Query("SELECT * FROM profile_detail_action")
    fun getAll(): List<ProfileDetailAction>

    @Query("SELECT * FROM profile_detail_action WHERE profile_detail_action_uid IN (:profileDetailActionIds)")
    fun getByIds(profileDetailActionIds: IntArray): List<ProfileDetailAction>

    @Query("SELECT * FROM profile_detail_action WHERE profile_uid LIKE (:profileId)")
    fun getByProfile(profileId: Long): List<ProfileDetailAction>

    @Insert
    fun insertAll(vararg profileDetailActions: ProfileDetailAction)

    @Delete
    fun delete(profileDetailAction: ProfileDetailAction)
}