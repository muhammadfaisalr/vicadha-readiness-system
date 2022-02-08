package id.muhammadfaisal.vicadhareadinesssystem.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityConfigBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.MessagingHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.GroupEntity
import id.muhammadfaisal.vicadhareadinesssystem.utils.*

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityConfigBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        try {
            this.startConfiguration()
        }catch (e: Exception) {
            Log.e(ConfigActivity::class.java.simpleName, e.message!!)
        }
    }

    private fun startConfiguration() {

        val groupDao = DatabaseHelper.RoomDb.groupDao(this)
        val groups: ArrayList<GroupEntity> = ArrayList()

        if (groupDao.getAll().isEmpty()) {

            groups.add(GroupEntity(null, "Kasdiv", null, R.drawable.logo_vicadha, "N", false, true))

            groups.add(GroupEntity(null, "Irdiv", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Irutum", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irutben", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irdya Intelter It", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irdya Osplat It", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irdya Perslog It", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irdya Renprogar It", "Irdiv", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Irdya Ku It", "Irdiv", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Asren", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Waasren", "Asren", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Renproggar", "Asren", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Anev", "Asren", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Asintel", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Waas Intel", "Asintel", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Lidgal", "Asintel", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Minintel", "Asintel", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Pam", "Asintel", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Asops", null, R.drawable.logo_vicadha, "N", true, true))
            groups.add(GroupEntity(null, "Waasops", "Asops", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Siapsat", "Asops", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi lat", "Asops", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Ops", "Asops", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Aspers", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Waaspers", "Aspers", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Binpers", "Aspers", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Binkar", "Aspers", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Kumtaltibprot", "Aspers", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Aslog", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Waaslog", "Aslog", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Bekang", "Aslog", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Mat", "Aslog", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Fasjasaslog", "Aslog", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Aster", null, R.drawable.logo_vicadha, "N", false, true))
            groups.add(GroupEntity(null, "Waaster", "Aster", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Puanter", "Aster", R.drawable.logo_vicadha, "N", false, false))
            groups.add(GroupEntity(null, "Kasi Binter", "Aster", R.drawable.logo_vicadha, "N", false, false))

            groups.add(GroupEntity(null, "Danbrig MR 6", null, GroupImages.DANBRIG_MR_6, "N", true, true))
            groups.add(GroupEntity(null, "Danyonif MR 411", "Danbrig MR 6", GroupImages.DANYONIF_MR_411, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif MR 412", "Danbrig MR 6", GroupImages.DANYONIF_MR_412, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif MR 413", "Danbrig MR 6",  GroupImages.DANYONIF_MR_413, "N", true, false))

            groups.add(GroupEntity(null, "Danbrig R 9", null, GroupImages.DANBRIG_R_9, "N", true, true))
            groups.add(GroupEntity(null, "Danyonif R 509", "Danbrig R 9", GroupImages.DANYONIF_R_509, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif R 514", "Danbrig R 9", GroupImages.DANYONIF_R_514, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif R 515", "Danbrig R 9", GroupImages.DANYONIF_R_515, "N", true, false))

            groups.add(GroupEntity(null, "Danbrig PR 18", null, GroupImages.DANBRIGIF_PR_18, "N", true, true))
            groups.add(GroupEntity(null, "Danyonif PR 501", "Danbrig PR 18", GroupImages.DANYONIF_PR_501, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif PR 502", "Danbrig PR 18", GroupImages.DANYONIF_PR_502, "N", true, false))
            groups.add(GroupEntity(null, "Danyonif PR 503", "Danbrig PR 18", GroupImages.DANYONIF_PR_503, "N", true, false))
            groups.add(GroupEntity(null, "Dandenpandutaikam PR 18", "Danbrig PR 18", GroupImages.DANDEPANDU_TAIKAM, "N", false, false))

            groups.add(GroupEntity(null, "Danmenarmed 2", null, GroupImages.DANMENARMED, "N", true, true))
            groups.add(GroupEntity(null, "Danyonarmed 1", "Danmenarmed 2", GroupImages.DANYONARMED1, "N", true, false))
            groups.add(GroupEntity(null, "Danyonarmed 11", "Danmenarmed 2", GroupImages.DANYONARMED11, "N", true, false))
            groups.add(GroupEntity(null, "Danyonadmed 12", "Danmenarmed 2", GroupImages.DANYONARMED12, "N", true, false))

            groups.add(GroupEntity(null, "Danyonkav 8", null, GroupImages.DANYONKAF8, "N", true, false))
            groups.add(GroupEntity(null, "Danyonarhanud 2", null, GroupImages.YONARHANUD, "N", true, false))
            groups.add(GroupEntity(null, "Danyonzipur 10", null, GroupImages.DANYONZIPUR, "N", true, false))
            groups.add(GroupEntity(null, "Danyonbekang 2", null, GroupImages.DANYONBEKANG, "N", true, false))
            groups.add(GroupEntity(null, "Danyonkes 2", null, GroupImages.DANYONKES2, "N", true, false))
            groups.add(GroupEntity(null, "Dandenma Divif 2", null, GroupImages.DANDENMA, "N", true, false))
            groups.add(GroupEntity(null, "Dandenpal Divif 2 K", null, GroupImages.DANDENPAL, "N", true, true))
            groups.add(GroupEntity(null, "Dandenhub Divif 2 K", null, GroupImages.DANDENHUB, "N", true, true))
            groups.add(GroupEntity(null, "Dandenpom Divif 2 K", null, GroupImages.DANDENPOM, "N", true, true))
            groups.add(GroupEntity(null, "Kaajen Divif 2 K", null, GroupImages.KAAJEN, "N", false, true))
            groups.add(GroupEntity(null, "Dankikav 8", null, GroupImages.DANKIKAV_8, "N", true, false))

            for (i in groups) {
                groupDao.insert(i)
            }

        }

        var sender = ""
        DatabaseHelper
            .Firebase
            .getAccountReference(AuthHelper.getUid()!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserFirebase::class.java)
                    val groupDao = DatabaseHelper.RoomDb.groupDao(this@ConfigActivity)

                    sender = user!!.name

                    //Save Important data to Shared Preferences.
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.USER_NAME, sender)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.ROLE_ID, user.roleId)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.GROUP_NAME, user.groupId)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.PHONE_NUMBER, user.phoneNumber)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.USER_EMAIL, user.email)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.USER_PASSWORD, Encrypter.encryptString(user.password)!!)

                    if (user.groupId.isNotEmpty()) {
                        val group = groupDao.get(user.groupId)!!
                        MessagingHelper.subscribe(GeneralHelper.setAsGroupId(user.groupId))

                        for (groupAdmin in groupDao.getAll()) {
                            if (group.name == groupAdmin.name) {
                                if (groupAdmin.isAdminOnly) {
                                    MessagingHelper.subscribe(GeneralHelper.setAsGroupId(Constant.RoleName.SUPER_ADMIN))
                                }
                            }
                        }

                        if (group.parentGroup != null) {
                            Log.d(ConfigActivity::class.java.simpleName, "${user.groupId} have a parent group ( ${group.parentGroup} )")
                            MessagingHelper.subscribe(GeneralHelper.setAsGroupId(group.parentGroup!!))
                        }
                    }else{
                        MessagingHelper.subscribe(GeneralHelper.setAsGroupId(Constant.RoleName.SUPER_ADMIN))
                    }

                    val bundle = Bundle()
                    bundle.putString(Constant.Key.GROUP_NAME, user.groupId)

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (user.roleId == Constant.Role.SUPER_ADMIN) {
                            MoveTo.home(this@ConfigActivity, null, true)
                        } else {
                            MoveTo.detailGroup(this@ConfigActivity, bundle, true)
                        }
                    }, 3000L)
                }

                override fun onCancelled(error: DatabaseError) {
                    BottomSheets.error(
                        this@ConfigActivity,
                        error.message,
                        error.details,
                        true
                    )
                }
            })

    }

    private fun getUserName() {

    }
}