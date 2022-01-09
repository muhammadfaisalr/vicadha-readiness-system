package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.VRSDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.GroupDao
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.UserDao

class DatabaseHelper {

    class RoomDb {
        companion object {
            private fun connect(context: Context): VRSDatabase {
                return Room.databaseBuilder(context, VRSDatabase::class.java, "vrs-database")
                    .allowMainThreadQueries().build()
            }

            fun userDao(context: Context): UserDao {
                val database = connect(context)

                return database.userDao()
            }

            fun groupDao(context: Context): GroupDao {
                val database = connect(context)

                return database.groupDao()
            }
        }
    }


    class Endpoint {
        companion object {
            const val USER = "USER"
            const val GROUP = "GROUP"
            const val TOKEN = "TOKEN"

            const val USERS = "users"
            const val MESSAGES = "messages"
        }
    }

    class Firebase {
        companion object {
            private const val DB_URL = "https://vicadha-readiness-system-default-rtdb.europe-west1.firebasedatabase.app/"
            fun getUserReference(): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.USER)
            }

            fun getAccountReference(uid: String): DatabaseReference {
                return getUserReference().child(uid)
            }

            fun getGroupReference(): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.GROUP)
            }

            fun getDetailGroupReference(groupName: String): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.GROUP).child(groupName)
            }

            fun getUserInGroupReference(groupName: String): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.GROUP).child(groupName)
                    .child(Endpoint.USERS)
            }

            fun getInboxReference(groupName: String): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.MESSAGES).child(groupName)
            }

            fun getTokenReference(): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.TOKEN)
            }

        }
    }
}