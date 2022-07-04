package id.muhammadfaisal.vicadhareadinesssystem.room.dao

import androidx.room.*
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM m_user")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM m_user WHERE group_name = :groupName")
    fun getAllByGroupName(groupName: String): List<UserEntity>

    @Query("SELECT * FROM m_user WHERE name = :name")
    fun get(name: String) : UserEntity

    @Query("SELECT * FROM m_user WHERE name LIKE :q AND group_name = :groupName")
    fun query(q: String, groupName: String) : List<UserEntity>

    @Query("SELECT * FROM m_user WHERE email = :email")
    fun getByEmail(email: String) : UserEntity

    @Insert
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Query("DELETE FROM m_user")
    fun deleteAll()
}