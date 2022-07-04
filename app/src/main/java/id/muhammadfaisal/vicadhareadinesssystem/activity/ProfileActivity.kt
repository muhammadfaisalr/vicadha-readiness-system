package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.bottomsheet.BottomSheetVerificationPassword
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityProfileBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.MessagingHelper
import id.muhammadfaisal.vicadhareadinesssystem.listener.ClickListener
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener, ClickListener {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityProfileBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.init()
    }

    private fun init() {
        var groupName =  SharedPreferences.get(this@ProfileActivity, Constant.Key.GROUP_NAME) as String
        val email = SharedPreferences.get(this@ProfileActivity, Constant.Key.USER_EMAIL) as String


        this.binding.apply {
            val userDao = DatabaseHelper.RoomDb.userDao(this@ProfileActivity)
            val user = userDao.getByEmail(email)

            if (groupName.isEmpty()) {
                groupName = GeneralHelper.getRoleName(user.roleId)
            }

            this.textGroupName.text = groupName
            this.textName.text = user.name
            this.textPhone.text = "+62${user.phoneNumber}"
            this.textInitial.text = GeneralHelper.textAvatar(user.name)

            GeneralHelper.makeClickable(this@ProfileActivity, this.textLogout, this.textEditProfile, this.textChangePassword)
        }
    }

    override fun onResume() {
        super.onResume()
        this.init()
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.textLogout) {
            this.logout()
        } else if (p0 == this.binding.textEditProfile) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.OPEN_FROM, ProfileActivity::class.java.simpleName)
            MoveTo.editProfile(this, bundle, false)
        } else if (p0 == this.binding.textChangePassword) {
            BottomSheets.verification(this, this)
        }
    }

    private fun logout() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        AuthHelper.Account.logout()
        val groupName = SharedPreferences.get(this, Constant.Key.GROUP_NAME) as String?

        if (groupName != null) {
            MessagingHelper.unsubscribe(groupName)
        }

        SharedPreferences.apply {
            delete(this@ProfileActivity, Constant.Key.GROUP_NAME)
            delete(this@ProfileActivity, Constant.Key.ROLE_ID)
            delete(this@ProfileActivity, Constant.Key.USER_NAME)
            delete(this@ProfileActivity, Constant.Key.PHONE_NUMBER)
            delete(this@ProfileActivity, Constant.Key.USER_EMAIL)
            delete(this@ProfileActivity, Constant.Key.USER_PASSWORD)
        }

        loading.dismiss()
        MoveTo.login(this, null, true)
    }

    override fun verification(password: String) {
        Log.d(ProfileActivity::class.java.simpleName, "Password inputted is ${Encrypter.encryptString(password)}")

        val savedPassword = Encrypter.decryptString(SharedPreferences.get(this, Constant.Key.USER_PASSWORD).toString())

        if (password == savedPassword) {
            MoveTo.changePassword(this, null, false)
        } else {
            BottomSheets.error(
                this,
                getString(R.string.wrong_password),
                getString(R.string.wrong_password_desc),
                true
            )
        }
    }
}