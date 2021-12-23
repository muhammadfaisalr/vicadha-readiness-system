package id.muhammadfaisal.vicadhareadinesssystem.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity

interface UserDao {

    @Query("SELECT * FROM m_user")
    fun getAll(): List<UserEntity>

    @Insert
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)
}