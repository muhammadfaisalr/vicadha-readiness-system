package id.muhammadfaisal.vicadhareadinesssystem.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.GroupEntity

@Dao
interface GroupDao {
    @Query("SELECT * FROM m_group")
    fun getAll(): List<GroupEntity>

    @Query("SELECT * FROM m_group WHERE parent_group IS NULL")
    fun getAllParent(): List<GroupEntity>

    @Query("SELECT * FROM m_group WHERE name = :name")
    fun get(name: String): GroupEntity?

    @Query("SELECT * FROM m_group WHERE parent_group= :groupName")
    fun getChildren(groupName: String): List<GroupEntity>

    @Query("SELECT name FROM m_group")
    fun getGroupName() : List<String>

    @Insert
    fun insert(groupEntity: GroupEntity)

    @Query("UPDATE m_group SET f_delete = 'Y' WHERE name = :groupName")
    fun delete(groupName: String)
}