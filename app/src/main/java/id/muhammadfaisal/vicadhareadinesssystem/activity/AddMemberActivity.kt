package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityAddMemberBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class AddMemberActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAddMemberBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {
            GeneralHelper.makeClickable(this@AddMemberActivity, this.exfabAdd)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabAdd){
            val bundle = Bundle()
            bundle.putString(Constant.Key.SUCCESS_TITLE, resources.getString(R.string.success_add_member))
            MoveTo.success(this, bundle, true)
        }
    }
}