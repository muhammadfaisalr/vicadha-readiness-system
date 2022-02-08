package id.muhammadfaisal.vicadhareadinesssystem

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.dao.UserDao
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Encrypter

class VRSApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        this.extract()
    }

    private fun extract() {
        val daoUser = DatabaseHelper.RoomDb.userDao(this)
        val usersInLocal = daoUser.getAll()

        DatabaseHelper.Firebase.getUserReference()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val sizeFirebase = snapshot.childrenCount

                    if (usersInLocal.isNotEmpty()) {
                        Log.d(VRSApplication::class.java.simpleName, "Users in Local is Not Empty")
                        if (sizeFirebase != usersInLocal.size.toLong()) {
                            daoUser.deleteAll()
                            this@VRSApplication.loopData(snapshot, daoUser, snapshot.key)
                        }
                    } else {
                        this@VRSApplication.loopData(snapshot, daoUser, snapshot.key)
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    BottomSheets.error(
                        this@VRSApplication,
                        error.message,
                        error.details,
                        true
                    )
                }

            })
    }

    private fun loopData(snapshot: DataSnapshot, daoUser: UserDao, key: String?) {
        Log.d(VRSApplication::class.java.simpleName, "Prepare for Insert to Local DB")

        for (data in snapshot.children) {
            val user = data.getValue(UserFirebase::class.java)!!
            daoUser.insert(
                UserEntity(
                    null,
                    user.email,
                    user.name,
                    user.phoneNumber,
                    user.groupId,
                    user.roleId.toInt(),
                    "N",
                    data.key,
                    Encrypter.encryptString(user.password)
                )
            )
            Log.d(VRSApplication::class.java.simpleName, "Insert to Local DB Was Successfully")
        }
    }
}