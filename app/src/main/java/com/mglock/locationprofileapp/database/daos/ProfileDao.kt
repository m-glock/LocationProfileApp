package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM profile WHERE uid IN (:profileIds)")
    fun getByIds(profileIds: IntArray): List<Profile>

    @Query("SELECT * FROM profile WHERE title LIKE (:title)")
    fun getByTitle(title: String): List<Profile>

    @Insert
    fun insertAll(vararg profiles: Profile)

    @Delete
    fun delete(profile: Profile)
}