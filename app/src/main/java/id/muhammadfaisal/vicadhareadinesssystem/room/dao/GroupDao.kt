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

    @Insert
    fun insert(groupEntity: GroupEntity)

    @Query("UPDATE m_group SET f_delete = 'Y' WHERE name = :groupName")
    fun delete(groupName: String)
}