package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.DetailAction
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailActionDao {
    @Query("SELECT * FROM detail_action")
    fun getAll(): Flow<List<DetailAction>>

    @Query("SELECT * FROM detail_action")
    suspend fun getAllExtra(): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    suspend fun getByIds(actionIds: LongArray): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE title LIKE (:title)")
    suspend fun getByTitle(title: String): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE profile_id LIKE (:profileID)")
    fun getByProfile(profileID: Long): Flow<List<DetailAction>>

    @Query("SELECT * FROM detail_action WHERE profile_id LIKE (:profileID)")
    suspend fun getByProfileOnce(profileID: Long): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE profile_id IS NULL")
    fun getByNoProfile(): Flow<List<DetailAction>>

    @Query("UPDATE detail_action SET profile_id = (:profileID) WHERE profile_id IS NULL")
    suspend fun addProfileId(profileID: Long)

    @Query("DELETE FROM detail_action WHERE profile_id IS NULL")
    suspend fun deleteAllWithNoProfile()

    @Update
    suspend fun update(detailAction: DetailAction)

    @Update
    suspend fun updateAll(vararg detailActions: DetailAction)

    @Insert
    fun insertAll(vararg detailActions: DetailAction)

    @Insert
    suspend fun insert(detailActions: DetailAction)

    @Delete
    suspend fun delete(detailActions: DetailAction)
}