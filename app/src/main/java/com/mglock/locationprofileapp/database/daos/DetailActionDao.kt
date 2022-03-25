package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.relations.DetailActionWithRelations

@Dao
interface DetailActionDao {
    @Query("SELECT * FROM detail_action")
    suspend fun getAll(): List<DetailAction>

    @Query("SELECT * FROM detail_action")
    suspend fun getAllWithRelations(): List<DetailActionWithRelations>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    suspend fun getByIds(actionIds: LongArray): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    suspend fun getByIdsWithRelations(actionIds: LongArray): List<DetailActionWithRelations>

    @Query("SELECT * FROM detail_action WHERE name LIKE (:name)")
    suspend fun getByName(name: String): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE name LIKE (:name)")
    suspend fun getByNameWithRelations(name: String): List<DetailActionWithRelations>

    @Insert
    fun insertAll(vararg detailActions: DetailAction)

    @Delete
    suspend fun delete(detailActions: DetailAction)
}