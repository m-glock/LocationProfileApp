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
    fun getAll(): List<DetailAction>

    @Query("SELECT * FROM detail_action")
    fun getAllWithRelations(): List<DetailActionWithRelations>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    fun getByIds(actionIds: LongArray): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE detail_action_uid IN (:actionIds)")
    fun getByIdsWithRelations(actionIds: LongArray): List<DetailActionWithRelations>

    @Query("SELECT * FROM detail_action WHERE name LIKE (:name)")
    fun getByName(name: String): List<DetailAction>

    @Query("SELECT * FROM detail_action WHERE name LIKE (:name)")
    fun getByNameWithRelations(name: String): List<DetailActionWithRelations>

    @Insert
    fun insertAll(vararg detailActions: DetailAction)

    @Delete
    fun delete(detailActions: DetailAction)
}