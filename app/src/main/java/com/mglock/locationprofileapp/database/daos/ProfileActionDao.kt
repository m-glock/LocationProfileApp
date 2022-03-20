package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.ProfileAction

@Dao
interface ProfileActionDao {
    @Query("SELECT * FROM profile_action")
    fun getAll(): List<ProfileAction>

    @Query("SELECT * FROM profile_action WHERE uid IN (:profileActionIds)")
    fun getByIds(profileActionIds: IntArray): List<ProfileAction>

    @Query("SELECT * FROM profile_action WHERE profile_id LIKE (:profileId)")
    fun getByProfile(profileId: Long): List<ProfileAction>

    @Insert
    fun insertAll(vararg profileActions: ProfileAction)

    @Delete
    fun delete(profileAction: ProfileAction)
}