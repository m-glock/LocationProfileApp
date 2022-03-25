package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.ActionGroup
import com.mglock.locationprofileapp.database.entities.relations.ActionGroupWithRelations

@Dao
interface ActionGroupDao {
    @Query("SELECT * FROM action_group")
    suspend fun getAll(): List<ActionGroup>

    @Query("SELECT * FROM action_group")
    suspend fun getAllWithRelations(): List<ActionGroupWithRelations>

    @Query("SELECT * FROM action_group WHERE action_group_uid IN (:actionGroupIds)")
    suspend fun getByIds(actionGroupIds: LongArray): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE action_group_uid IN (:actionGroupIds)")
    suspend fun getByIdsWithRelations(actionGroupIds: LongArray): List<ActionGroupWithRelations>

    @Query("SELECT * FROM action_group WHERE title LIKE (:name)")
    suspend fun getByTitle(name: String): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE title LIKE (:name)")
    suspend fun getByTitleWithRelations(name: String): List<ActionGroupWithRelations>

    @Query("SELECT * FROM action_group WHERE active LIKE 1")
    suspend fun getActive(): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE active LIKE 1")
    suspend fun getActiveWithRelations(): List<ActionGroupWithRelations>

    @Update
    suspend fun update(actionGroup: ActionGroup)

    @Insert
    fun insertAll(vararg actionGroups: ActionGroup)

    @Delete
    suspend fun delete(actionGroup: ActionGroup)
}