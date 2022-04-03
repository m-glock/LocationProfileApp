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

    @Transaction
    @Query("SELECT * FROM profile WHERE place_id LIKE (:placeId)")
    suspend fun getByPlaceWithRelations(placeId: Long): List<ProfileWithRelations>

    @Update
    suspend fun update(profile: Profile)

    @Insert
    suspend fun insert(profile: Profile): Long

    @Insert
    fun insertAll(vararg profiles: Profile)

    @Delete
    suspend fun delete(profile: Profile)
}