package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityLoginBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        if (AuthHelper.getCurrentUser() != null) {
            MoveTo.home(this, null, true)
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
        var email = ""
        var password = ""
        this.binding.apply {
            email = binding.inputEmail.text.toString()
            password = binding.inputPassword.text.toString()
        }

        if (email.isEmpty() || password.isEmpty()) {
            BottomSheets.error(this, getString(R.string.can_t_login), getString(R.string.can_t_login_desc), true)
            return
        }

        AuthHelper.Account.signInWithEmailPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                MoveTo.config(this, null, true)
            }else{
                BottomSheets.error(this, it.exception?.message!!, null, true)
            }
        }

    }
}