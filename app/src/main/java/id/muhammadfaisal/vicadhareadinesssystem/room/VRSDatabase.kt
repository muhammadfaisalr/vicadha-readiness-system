package id.muhammadfaisal.vicadhareadinesssystem.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.GroupDao
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.UserDao
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.GroupEntity
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity

@Database(entities = [UserEntity::class, GroupEntity::class], version = 1)
abstract class VRSDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    abstract fun groupDao() : GroupDao
}