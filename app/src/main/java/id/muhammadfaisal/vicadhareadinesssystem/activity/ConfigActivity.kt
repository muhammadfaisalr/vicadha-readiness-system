package id.muhammadfaisal.vicadhareadinesssystem.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityConfigBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityConfigBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.startConfiguration()
    }

    private fun startConfiguration() {
        var sender = ""
        DatabaseHelper
            .Firebase
            .getAccountReference(AuthHelper.getUid()!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserFirebase::class.java)
                    sender = user!!.name

                    SharedPreferences.save(this@ConfigActivity, Constant.Key.USER_NAME, sender)
                    SharedPreferences.save(this@ConfigActivity, Constant.Key.ROLE_ID, user.roleId)

                    val bundle = Bundle()
                    bundle.putString(Constant.Key.GROUP_NAME, user.groupId)

                    if (user.roleId == Constant.Role.SUPER_ADMIN) {
                        MoveTo.detailGroup(this@ConfigActivity, null, true)
                    } else {
                        MoveTo.detailGroup(this@ConfigActivity, bundle, true)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    BottomSheets.error(
                        this@ConfigActivity,
                        error.message,
                        error.details,
                        true
                    ).run {
                        MoveTo.login(this@ConfigActivity, null, true    )
                    }
                }
            })

    }

    private fun getUserName() {

    }
}