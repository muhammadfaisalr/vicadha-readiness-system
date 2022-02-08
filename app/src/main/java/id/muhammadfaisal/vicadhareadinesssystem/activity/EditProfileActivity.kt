package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.MemberAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityEditProfileBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.ViewHelper
import id.muhammadfaisal.vicadhareadinesssystem.room.entity.UserEntity
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.Encrypter
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityEditProfileBinding

    private lateinit var userEntity: UserEntity

    private var openFrom = ""
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityEditProfileBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()
        this.setup()

        this.binding.apply {
            GeneralHelper.makeClickable(this@EditProfileActivity, this.imageLeft, this.exfabSave)
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.openFrom = bundle.getString(Constant.Key.OPEN_FROM, "")
        this.email = bundle.getString(Constant.Key.USER_EMAIL, SharedPreferences.get(this, Constant.Key.USER_EMAIL).toString())

        if (openFrom == ProfileActivity::class.java.simpleName) {
            this.binding.apply {
                ViewHelper.hide(this.inputRole, this.layoutRole)
            }
        } else {
            this.binding.apply {
                ViewHelper.disable(this.inputEmail, this.inputName, this.inputPhone)
            }
        }
    }

    private fun setup() {
        val userDao = DatabaseHelper.RoomDb.userDao(this)
        val user = userDao.getByEmail(email = email)

        val roles = ArrayList<String>()
        roles.add(Constant.RoleName.SUPER_ADMIN)
        roles.add(Constant.RoleName.ADMIN)
        roles.add(Constant.RoleName.MEMBER)

        this.binding.apply {
            this.inputEmail.setText(user.email)
            this.inputPhone.setText(user.phoneNumber.toString())
            this.inputName.setText(user.name)
            this.inputRole.setText(GeneralHelper.getRoleName(user.roleId))
            this.inputRole.setAdapter(ArrayAdapter(this@EditProfileActivity, R.layout.support_simple_spinner_dropdown_item, roles))
        }

        this.userEntity = user;
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageLeft) {
            this.finish()
        } else if (p0 == this.binding.exfabSave) {
            this.save()
        }
    }

    private fun save() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val roleName = this.binding.inputRole.text.toString()
        val name = this.binding.inputName.text.toString()
        val phone = this.binding.inputPhone.text.toString()

        val isPassed = this.validateData(roleName, name, phone)

        if (!isPassed) {
            loading.dismiss()

            BottomSheets.error(
                this,
                getString(R.string.something_wrong),
                getString(R.string.make_sure_fill_was_filled),
                true
            )

            return
        }

        val userDao = DatabaseHelper.RoomDb.userDao(this)

        this.userEntity.roleId = GeneralHelper.getRoleId(roleName)
        this.userEntity.name = name
        this.userEntity.phoneNumber = phone.toLong()

        //Update to Local DB
        Log.d(EditProfileActivity::class.java.simpleName, "Ready to update [DATA] : $userEntity")
        userDao.update(this.userEntity)

        val userFirebase = UserFirebase(
            this.userEntity.phoneNumber,
            this.userEntity.name,
            this.userEntity.email,
            Encrypter.decryptString(this.userEntity.password),
            this.userEntity.groupName!!,
            this.userEntity.roleId,
            false
        )

        //Progress Update to Firebase DB.
        DatabaseHelper
            .Firebase
            .getAccountReference(this.userEntity.key!!)
            .setValue(userFirebase)
            .addOnCompleteListener {
                Log.d(EditProfileActivity::class.java.simpleName, "Success Update Member Data.!")
                Toast.makeText(this, getString(R.string.success_update_data), Toast.LENGTH_SHORT).show()
                loading.dismiss()

                finish()
            }
            .addOnFailureListener {
                Log.e(EditProfileActivity::class.java.simpleName, it.message!!)

                loading.dismiss()
                BottomSheets.error(
                    this,
                    getString(R.string.something_wrong),
                    it.message,
                    true
                )
            }
    }

    private fun validateData(roleName: String, name: String, phone: String): Boolean {
        return when {
            roleName.isEmpty() -> {
                false
            }
            name.isEmpty() -> {
                false
            }
            phone.isEmpty() -> {
                false
            }
            else -> true
        }

    }
}