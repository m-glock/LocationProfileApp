package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.ActionGroup

@Dao
interface ActionGroupDao {
    @Query("SELECT * FROM action_group")
    suspend fun getAll(): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE uid IN (:actionGroupIds)")
    suspend fun getByIds(actionGroupIds: IntArray): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE title LIKE (:name)")
    suspend fun getByTitle(name: String): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE active LIKE 1")
    suspend fun getActive(): List<ActionGroup>

    @Query("UPDATE action_group SET active = (:active) WHERE title LIKE (:title)")
    suspend fun update(active: Boolean, title: String)

    @Insert
    fun insertAll(vararg actionGroups: ActionGroup)

    @Delete
    suspend fun delete(actionGroup: ActionGroup)
}