package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): Flow<List<Profile>>

    @Transaction
    @Query("SELECT * FROM profile")
    fun getAllWithRelations(): Flow<List<ProfileWithRelations>>

    @Query("SELECT * FROM profile WHERE profile_uid IN (:profileIds)")
    suspend fun getByIds(profileIds: LongArray): List<Profile>

    @Query("SELECT * FROM profile WHERE profile_uid IN (:profileIds)")
    suspend fun getByIdsWithRelations(profileIds: LongArray): List<ProfileWithRelations>

    @Query("SELECT * FROM profile WHERE title LIKE (:title)")
    suspend fun getByTitle(title: String): List<Profile>

    @Query("SELECT * FROM profile WHERE title LIKE (:title)")
    suspend fun getByTitleWithRelations(title: String): List<ProfileWithRelations>

    @Update
    fun update(profile: Profile)

    @Insert
    fun insertAll(vararg profiles: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}