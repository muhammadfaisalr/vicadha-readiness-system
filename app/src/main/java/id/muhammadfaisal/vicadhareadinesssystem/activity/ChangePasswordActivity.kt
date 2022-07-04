package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityChangePasswordBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.Encrypter
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class ChangePasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityChangePasswordBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {
            GeneralHelper.makeClickable(this@ChangePasswordActivity, this.imageLeft, this.exfabSave)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageLeft) {
            this.finish()
        } else if (p0 == this.binding.exfabSave) {
            this.save()
        }
    }

    private fun save() {
        val loading = Loading(this);
        loading.setCancelable(false)
        loading.show()

        val password = this.binding.inputPassword.text.toString()
        val confirmPassword = this.binding.inputConfirmPassword.text.toString()

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            BottomSheets.error(this, getString(R.string.something_wrong), getString(R.string.make_sure_fill_was_filled), true)
            return
        }

        if (password == confirmPassword) {
            Log.d(ChangePasswordActivity::class.java.simpleName, "Processing Change Password Request.")

            val userDao = DatabaseHelper.RoomDb.userDao(this)
            val email = SharedPreferences.get(this, Constant.Key.USER_EMAIL).toString()
            val user = userDao.getByEmail(email)

            user.password = Encrypter.encryptString(password)
            userDao.update(user)

            var next = true
            DatabaseHelper
                .Firebase
                .getAccountReference(user.key!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userFirebase = snapshot.getValue(UserFirebase::class.java)

                        if (userFirebase != null) {
                            userFirebase.password = password

                            if (next) {

                                DatabaseHelper
                                    .Firebase
                                    .getAccountReference(user.key!!)
                                    .setValue(userFirebase)
                                    .addOnSuccessListener {

                                        AuthHelper
                                            .Account
                                            .updatePassword(password)
                                            .addOnCompleteListener {
                                                loading.dismiss()
                                                SharedPreferences.save(this@ChangePasswordActivity, Constant.Key.USER_PASSWORD, Encrypter.encryptString(password).toString());
                                                Toast.makeText(
                                                    this@ChangePasswordActivity,
                                                    "Berhasil Update Kata Sandi!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                finish()
                                            }

                                            .addOnFailureListener {
                                                loading.dismiss()
                                                BottomSheets.error(
                                                    this@ChangePasswordActivity,
                                                    getString(R.string.something_wrong),
                                                    it.message,
                                                    true
                                                )
                                            }

                                    }
                                    .addOnFailureListener {
                                        loading.dismiss()
                                        BottomSheets.error(this@ChangePasswordActivity, getString(R.string.something_wrong), it.message, true)
                                    }
                                next = false
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        loading.dismiss()
                    }

                })
        }else{
            BottomSheets.error(
                this,
                getString(R.string.password_not_equal),
                getString(R.string.password_not_equal_desc),
                true
            )
        }
    }
}