package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    suspend fun getAll(): List<Profile>

    @Transaction
    @Query("SELECT * FROM profile")
    suspend fun getAllWithRelations(): List<ProfileWithRelations>

    @Query("SELECT * FROM profile WHERE profile_uid IN (:profileIds)")
    suspend fun getByIds(profileIds: LongArray): List<Profile>

    @Query("SELECT * FROM profile WHERE profile_uid IN (:profileIds)")
    suspend fun getByIdsWithRelations(profileIds: LongArray): List<ProfileWithRelations>

    @Query("SELECT * FROM profile WHERE title LIKE (:title)")
    suspend fun getByTitle(title: String): List<Profile>

    @Query("SELECT * FROM profile WHERE title LIKE (:title)")
    suspend fun getByTitleWithRelations(title: String): List<ProfileWithRelations>

    @Insert
    fun insertAll(vararg profiles: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}