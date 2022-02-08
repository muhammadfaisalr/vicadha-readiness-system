package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityAddMemberBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class AddMemberActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddMemberBinding

    private lateinit var bundle: Bundle

    private var roleId = 0
    private var groupName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddMemberBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.roleId = this.bundle.getInt(Constant.Key.ROLE_ID, 0)
        this.groupName = this.bundle.getString(Constant.Key.FOR_GROUP, "");

        val groupDao = DatabaseHelper.RoomDb.groupDao(this)

        this.binding.apply {
            this.textGroupName.text = groupName

            when (roleId) {
                Constant.Role.ADMIN -> {
                    this.textTitle.text = getString(R.string.add_admin)
                }
                Constant.Role.SUPER_ADMIN -> {
                    this.textTitle.text = getString(R.string.add_super_admin)
                }
                else -> {
                    this.textTitle.text = getString(R.string.add_member)
                }
            }

            if (groupName == "") {
                val arrayAdapter = ArrayAdapter(this@AddMemberActivity, R.layout.support_simple_spinner_dropdown_item, groupDao.getGroupName())
                this.inputChooseGroup.setAdapter(arrayAdapter)
            }else{
                this.layoutGroup.visibility = View.GONE
                this.inputChooseGroup.visibility = View.GONE
            }

            GeneralHelper.makeClickable(this@AddMemberActivity, this.exfabAdd, this.textPassDefault)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabAdd) {
            this.addMember()
        }

        if (p0 == this.binding.textPassDefault) {
            this.setPassDefault()
        }
     }

    private fun setPassDefault() {
        this.binding.apply {
            this.inputPassword.setText("123456")
            this.inputConfirmPassword.setText("123456")
        }
    }

    private fun addMember() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val email = this.binding.inputEmail.text.toString()
        val password = this.binding.inputPassword.text.toString()
        val passwordConfirmed = this.binding.inputPassword.text.toString()
        val name = this.binding.inputName.text.toString()
        val phone = this.binding.inputPhone.text.toString()

        val groupName = if (this.groupName == "") this.binding.inputChooseGroup.text.toString() else this.groupName

        val dao = DatabaseHelper.RoomDb.userDao(this)
        val groupDao = DatabaseHelper.RoomDb.groupDao(this)

        if (groupDao.get(groupName) == null) {
            loading.dismiss()
            BottomSheets.error(
                this,
                getString(R.string.something_wrong),
                getString(R.string.group_not_found),
                true
            )
            return
        }

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || passwordConfirmed.isEmpty()) {
            loading.dismiss()
            BottomSheets.error(
                this,
                getString(R.string.can_t_create_account),
                getString(R.string.can_t_create_account_desc),
                true
            )
            return
        }


        val userFirebase = UserFirebase(phone.toLong(), name, email, password, groupName, this.roleId, false)
        AuthHelper.Account.createAccount(userFirebase).addOnCompleteListener {
            if (it.isSuccessful) {
                val headIt = it
                dao.insert(UserEntity(null, email, name, phone.toLong(), groupName, this.roleId, null, headIt.result!!.user!!.uid, password))

                DatabaseHelper
                    .Firebase
                    .getAccountReference(headIt.result!!.user!!.uid)
                    .setValue(userFirebase)
                    .addOnCompleteListener {
                        loading.dismiss()

                        val bundle = Bundle()
                        bundle.putString(Constant.Key.SUCCESS_TITLE, resources.getString(R.string.success_add_member))
                        MoveTo.success(this, bundle, true)
                    }
                    .addOnFailureListener {
                        loading.dismiss()
                        BottomSheets.error(this, getString(R.string.something_wrong), it.message, true)
                    }
            } else {
                loading.dismiss()
                BottomSheets.error(this, it.exception?.message!!, null, true)
            }
        }

    }
}