package com.mglock.databasetest.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.databasetest.entities.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM profile WHERE uid IN (:profileIds)")
    fun getByIds(profileIds: IntArray): List<Profile>

    @Query("SELECT * FROM profile WHERE name LIKE (:name)")
    fun getByTitle(name: String): List<Profile>

    @Insert
    fun insertAll(vararg profiles: Profile)

    @Delete
    fun delete(profile: Profile)
}