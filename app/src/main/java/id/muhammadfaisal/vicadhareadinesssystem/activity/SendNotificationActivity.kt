package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivitySendNotificationBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.ui.Loading
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MessagingService
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class SendNotificationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySendNotificationBinding
    private lateinit var groupName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySendNotificationBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()
        this.binding.apply {
            GeneralHelper.makeClickable(this@SendNotificationActivity, this.exfabSend)
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.groupName = bundle.getString(Constant.Key.GROUP_NAME)!!
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabSend) {
            this.send()
        }
    }

    private fun send() {
        val loading = Loading(this)
        loading.setCancelable(false)
        loading.show()

        val title : String?

        when (this.binding.groupRadio.checkedRadioButtonId) {
            R.id.radioApelKesiapsiagaan -> {
                title = this.binding.radioApelKesiapsiagaan.text.toString()
            }
            R.id.radioApelLuarBiasa -> {
                title = this.binding.radioApelLuarBiasa.text.toString()
            }
            R.id.radioBantuanKepolisian -> {
                title = this.binding.radioBantuanKepolisian.text.toString()
            }
            R.id.radioAlarmStelling -> {
                title = this.binding.radioAlarmStelling.text.toString()
            }
            R.id.radioBahayaKebakaran -> {
                title = this.binding.radioBahayaKebakaran.text.toString()
            }
            R.id.radioBencanaAlam -> {
                title = this.binding.radioBencanaAlam.text.toString()
            }
            else -> {
                title = this.binding.radioTandaAman.text.toString()
            }
        }

        val contentMessage = "$title Telah Diserukan. Segera Laksanakan!"
        val sender = SharedPreferences.get(this, Constant.Key.USER_NAME) as String
        val message = MessageFirebase(title, contentMessage, sender, System.currentTimeMillis(), Constant.MessageType.IMPORTANT)

        Log.d(SendNotificationActivity::class.java.simpleName, this.groupName)
        MessagingService.sendMessage(message, groupName, GeneralHelper.setAsGroupId(this.groupName))


        val messages = ArrayList<MessageFirebase>()

        var isCanContinue = true
        var index = 0
        DatabaseHelper
            .Firebase
            .getInboxReference(this.groupName)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("Loop", "${index++}")
                    messages.add(message)

                    loading.dismiss()
                    for (data in snapshot.children) {
                        val messageChildren = data.getValue(MessageFirebase::class.java)
                        messages.add(messageChildren!!)
                    }


                    if (isCanContinue) {
                        DatabaseHelper
                            .Firebase
                            .getInboxReference(this@SendNotificationActivity.groupName)
                            .setValue(messages)
                            .addOnSuccessListener {
                                //Send Notification to SuperAdmin
                                message.title = "Alarm Baru pada $groupName"
                                message.message = "Alarm $title di $groupName Baru Saja Di Serukan, Segera Periksa!."
                                MessagingService.sendMessage(message, groupName, GeneralHelper.setAsGroupId(Constant.RoleName.SUPER_ADMIN))

                                val bundle = Bundle()
                                bundle.putString(Constant.Key.SUCCESS_TITLE, resources.getString(R.string.notification_sended))
                                MoveTo.success(this@SendNotificationActivity, bundle, true)
                            }
                    }

                    isCanContinue = false
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}