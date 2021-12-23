package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Context
import androidx.room.Room
import id.muhammadfaisal.vicadhareadinesssystem.room.VRSDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.UserDao

class DatabaseHelper {

    class RoomDb {
        companion object {
            private fun connect(context: Context) : VRSDatabase {
                return Room.databaseBuilder(context, VRSDatabase::class.java, "vrs-database").allowMainThreadQueries().build()
            }

            fun userDao(context: Context): UserDao {
                val database = connect(context)

                return database.userDao()
            }
        }
    }

    class Firebase {

    }
}