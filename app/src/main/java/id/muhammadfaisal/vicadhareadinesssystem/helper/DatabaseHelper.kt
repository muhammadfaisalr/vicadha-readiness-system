package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.VRSDatabase
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.GroupDao
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.UserDao

class DatabaseHelper {

    private class Migrate{
        companion object {
            val migration1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE m_user ADD COLUMN `key` VARCHAR")
                }
            }

            val migration2_3 = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE m_user ADD COLUMN password VARCHAR")
                }
            }
        }
    }

    class RoomDb {
        companion object {
            private fun connect(context: Context): VRSDatabase {
                return Room.databaseBuilder(context, VRSDatabase::class.java, "vrs-database")
                    .allowMainThreadQueries().addMigrations(Migrate.migration1_2, Migrate.migration2_3).build()
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
            const val DOCUMENT = "DOCUMENT"

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

            fun getDocumentReference(groupName: String): DatabaseReference {
                return FirebaseDatabase.getInstance(DB_URL).getReference(Endpoint.DOCUMENT).child(groupName)
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