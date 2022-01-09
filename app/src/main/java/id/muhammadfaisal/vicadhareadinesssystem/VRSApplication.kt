package id.muhammadfaisal.vicadhareadinesssystem

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.GroupEntity
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.GroupImages
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class VRSApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        this.extract()
    }

    private fun extract() {
        val data = SharedPreferences.get(this, Constant.Key.IS_FIRST_OPEN)

        if (data == null) {
            val groupDao = DatabaseHelper.RoomDb.groupDao(this)
            val groups: ArrayList<GroupEntity> = ArrayList()
            groups.add(GroupEntity(null, "Kasdiv", null, R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Irdiv", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irutum", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irutben", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irdya Intelter It", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irdya Osplat It", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irdya Perslog It", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irdya Renprogar It", "Irdiv", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Irdya Ku It", "Irdiv", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Asren", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Wasren", "Asren", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Renproggar", "Asren", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Anev", "Asren", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Asintel", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waas Intel", "Asintel", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waas Lidgal", "Asintel", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waas Minintel", "Asintel", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waas Pam", "Asintel", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Asops", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waasops", "Asops", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Siapsat", "Asops", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasilat", "Asops", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Ops", "Asops", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Aspers", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waaspers", "Aspers", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Binpers", "Aspers", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Binkar", "Aspers", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Kumaltibprot", "Aspers", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Aslog", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waaslog", "Aslog", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Bekang", "Aslog", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Mat", "Aslog", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Fasjasaslog", "Aslog", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Aster", null, R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Waaster", "Aster", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Puanter", "Aster", R.drawable.logo_vicadha, "N"))
            groups.add(GroupEntity(null, "Kasi Binter", "Aster", R.drawable.logo_vicadha, "N"))

            groups.add(GroupEntity(null, "Danbrig MR 6", null, GroupImages.DANBRIG_MR_6, "N"))
            groups.add(GroupEntity(null, "Danyonif MR 411", "Danbrig MR 6", GroupImages.DANYONIF_MR_411, "N"))
            groups.add(GroupEntity(null, "Danyonif MR 412", "Danbrig MR 6", GroupImages.DANYONIF_MR_412, "N"))
            groups.add(GroupEntity(null, "Danyonif MR 413", "Danbrig MR 6",  GroupImages.DANYONIF_MR_413, "N"))

            groups.add(GroupEntity(null, "Danbrig R 9", null, GroupImages.DANBRIG_R_9, "N"))
            groups.add(GroupEntity(null, "Danyonif R 509", "Danbrig R 9", GroupImages.DANYONIF_R_509, "N"))
            groups.add(GroupEntity(null, "Danyonif R 514", "Danbrig R 9", GroupImages.DANYONIF_R_514, "N"))
            groups.add(GroupEntity(null, "Danyonif R 515", "Danbrig R 9", GroupImages.DANYONIF_R_515, "N"))

            groups.add(GroupEntity(null, "Danbrig PR 18", null, GroupImages.DANBRIGIF_PR_18, "N"))
            groups.add(GroupEntity(null, "Danyonif PR 501", "Danbrig PR 18", GroupImages.DANYONIF_PR_501, "N"))
            groups.add(GroupEntity(null, "Danyonif PR 502", "Danbrig PR 18", GroupImages.DANYONIF_PR_502, "N"))
            groups.add(GroupEntity(null, "Danyonif PR 503", "Danbrig PR 18", GroupImages.DANYONIF_PR_503, "N"))
            groups.add(GroupEntity(null, "Dandenpandutaikam PR 18", "Danbrig PR 18", GroupImages.DANDEPANDU_TAIKAM, "N"))

            groups.add(GroupEntity(null, "Danmenarmed 2", null, GroupImages.DANMENARMED, "N"))
            groups.add(GroupEntity(null, "Danyonarmed 1", "Danmenarmed 2", GroupImages.DANYONARMED1, "N"))
            groups.add(GroupEntity(null, "Danyonarmed 11", "Danmenarmed 2", GroupImages.DANYONARMED11, "N"))
            groups.add(GroupEntity(null, "Danyonadmed 12", "Danmenarmed 2", GroupImages.DANYONARMED12, "N"))

            groups.add(GroupEntity(null, "Danyonkav 8", null, GroupImages.DANYONKAF8, "N"))
            groups.add(GroupEntity(null, "Danyonarhanud 2", null, GroupImages.YONARHANUD, "N"))
            groups.add(GroupEntity(null, "Danyonzipur 10", null, GroupImages.DANYONZIPUR, "N"))
            groups.add(GroupEntity(null, "Danyonbekang 2", null, GroupImages.DANYONBEKANG, "N"))
            groups.add(GroupEntity(null, "Danyonkes 2", null, GroupImages.DANYONKES2, "N"))
            groups.add(GroupEntity(null, "Dandenma Divif 2", null, GroupImages.DANDENMA, "N"))
            groups.add(GroupEntity(null, "Dandenpal Divif 2", null, GroupImages.DANDENPAL, "N"))
            groups.add(GroupEntity(null, "Dandenhub Divif 2", null, GroupImages.DANDENHUB, "N"))
            groups.add(GroupEntity(null, "Dandenpom Divif 2", null, GroupImages.DANDENPOM, "N"))
            groups.add(GroupEntity(null, "Kaajen Divif 2 K", null, GroupImages.KAAJEN, "N"))
            groups.add(GroupEntity(null, "Dankikav 8", null, GroupImages.DANKIKAV_8, "N"))

            for (i in groups) {
                groupDao.insert(i)
            }

            SharedPreferences.save(this, Constant.Key.IS_FIRST_OPEN, Constant.Key.NO)
        }

        val daoUser = DatabaseHelper.RoomDb.userDao(this)
        val usersInLocal = daoUser.getAll()

        DatabaseHelper.Firebase.getUserReference().addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val users = dataSnapshot.getValue(UserFirebase::class.java)
                    if (usersInLocal.isNotEmpty()) {
                        Log.d(VRSApplication::class.java.simpleName, "Users in Local is Not Empty")
                        for (i in usersInLocal){
                            daoUser.delete(i)
                        }
                    }
                    Log.d(VRSApplication::class.java.simpleName, "Prepare for Insert to Local DB")

                    users!!
                    daoUser.insert(UserEntity(null, users.email, users.name, users.phoneNumber, users.groupId, users.roleId.toInt(), "N"))
                    Log.d(VRSApplication::class.java.simpleName, "Insert to Local DB Was Successfully")
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
}