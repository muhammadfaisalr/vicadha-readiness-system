package id.muhammadfaisal.vicadhareadinesssystem.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM m_user")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM m_user WHERE group_name = :groupName")
    fun getAllByGroupName(groupName: String): List<UserEntity>

    @Insert
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)
}