package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityLoginBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (AuthHelper.getCurrentUser() != null) {
            val roleId = SharedPreferences.get(this, Constant.Key.ROLE_ID) as Int
            val groupName = SharedPreferences.get(this, Constant.Key.GROUP_NAME) as String

            if (roleId == Constant.Role.SUPER_ADMIN) {
                MoveTo.home(this, null, true)
                return
            }

            val bundle = Bundle()
            bundle.putString(Constant.Key.GROUP_NAME, groupName)
            MoveTo.detailGroup(this, bundle,true)
        }

        this.binding.apply {
            GeneralHelper.makeClickable( this@LoginActivity, this.buttonLogin)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonLogin) {
            this.login()
        }
    }

    private fun login() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        var email = ""
        var password = ""
        this.binding.apply {
            email = binding.inputEmail.text.toString().replace(" ", "")
            password = binding.inputPassword.text.toString()
        }

        if (email.isEmpty() || password.isEmpty()) {
            BottomSheets.error(this, getString(R.string.can_t_login), getString(R.string.can_t_login_desc), true)
            return
        }

        AuthHelper.Account.signInWithEmailPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                loading.dismiss()
                MoveTo.config(this, null, true)
            }else{
                loading.dismiss()
                BottomSheets.error(this, it.exception?.message!!, null, true)
            }
        }

    }
}