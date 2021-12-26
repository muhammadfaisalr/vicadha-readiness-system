package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityLoginBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {
            GeneralHelper.makeClickable( this@LoginActivity, this.buttonLogin)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonLogin) {
            MoveTo.home(this, null, true)
        }
    }
}