package com.mglock.databasetest.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.databasetest.entities.SubAction

@Dao
interface SubActionDao {
    @Query("SELECT * FROM sub_action")
    fun getAll(): List<SubAction>

    @Query("SELECT * FROM sub_action WHERE uid IN (:actionIds)")
    fun getByIds(actionIds: LongArray): List<SubAction>

    @Query("SELECT * FROM sub_action WHERE action_group_id In (:actionGroupIds)")
    fun getByActionGroup(actionGroupIds: Long): List<SubAction>

    @Query("SELECT * FROM sub_action WHERE name LIKE (:name)")
    fun getByName(name: String): List<SubAction>

    @Insert
    fun insertAll(vararg subActions: SubAction)

    @Delete
    fun delete(subAction: SubAction)
}