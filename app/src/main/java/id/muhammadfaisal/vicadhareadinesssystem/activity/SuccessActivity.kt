package id.muhammadfaisal.vicadhareadinesssystem.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivitySuccessBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySuccessBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()

        val roleId = SharedPreferences.get(this, Constant.Key.ROLE_ID)
        val groupName = SharedPreferences.get(this, Constant.Key.GROUP_NAME)

        Log.d(SuccessActivity::class.java.simpleName, "The Role is $roleId (Note : 2 Super Admin | 1 Admin | 0 Member)")
        Handler(Looper.myLooper()!!).postDelayed({
            if (roleId == Constant.Role.SUPER_ADMIN) {
                MoveTo.home(this, null, true)
            }else{
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, groupName.toString())
                bundle.putBoolean(Constant.Key.IS_FROM_NOTIFICATION, false)

                MoveTo.detailGroup(this, bundle, true)
            }
        }, 3000L)
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)
        this.binding.textTitle.text = bundle?.getString(Constant.Key.SUCCESS_TITLE)
    }
}