package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.ActionGroup

@Dao
interface ActionGroupDao {
    @Query("SELECT * FROM action_group")
    fun getAll(): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE uid IN (:actionGroupIds)")
    fun getByIds(actionGroupIds: IntArray): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE name LIKE (:name)")
    fun getByTitle(name: String): List<ActionGroup>

    @Query("SELECT * FROM action_group WHERE active LIKE 1")
    fun getActive(): List<ActionGroup>

    @Insert
    fun insertAll(vararg actionGroups: ActionGroup)

    @Delete
    fun delete(actionGroup: ActionGroup)
}