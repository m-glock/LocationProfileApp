package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mglock.locationprofileapp.database.entities.ActionGroup

@Dao
interface ActionGroupDao {
    @Query("SELECT * FROM action_group")
    suspend fun getAll(): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE action_group_uid IN (:actionGroupIds)")
    suspend fun getByIds(actionGroupIds: IntArray): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE title LIKE (:name)")
    suspend fun getByTitle(name: String): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE active LIKE 1")
    suspend fun getActive(): List<ActionGroup>

    @Update
    suspend fun update(actionGroup: ActionGroup)

    @Insert
    fun insertAll(vararg actionGroups: ActionGroup)

    @Delete
    suspend fun delete(actionGroup: ActionGroup)
}