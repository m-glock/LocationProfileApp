package com.mglock.locationprofileapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mglock.locationprofileapp.database.entities.DetailAction

@Dao
interface DetailActionDao {
    @Query("SELECT * FROM detail_action")
    fun getAll(): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    fun getByIds(actionIds: LongArray): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE action_group_id In (:actionGroupIds)")
    fun getByActionGroup(actionGroupIds: Long): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE name LIKE (:name)")
    fun getByName(name: String): List<DetailAction>

    @Insert
    fun insertAll(vararg detailActions: DetailAction)

    @Delete
    fun delete(detailActions: DetailAction)
}