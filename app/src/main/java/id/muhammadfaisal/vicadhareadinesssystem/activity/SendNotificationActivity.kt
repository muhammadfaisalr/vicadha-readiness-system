package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivitySendNotificationBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class SendNotificationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySendNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySendNotificationBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {
            GeneralHelper.makeClickable(this@SendNotificationActivity, this.exfabSend)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabSend) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.SUCCESS_TITLE, resources.getString(R.string.notification_sended))
            MoveTo.success(this, bundle, true)
        }
    }
}