package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityWriteMessageBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.UserFirebase
import id.muhammadfaisal.vicadhareadinesssystem.helper.AuthHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.BottomSheets
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class WriteMessageActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWriteMessageBinding

    private lateinit var bundle: Bundle
    private lateinit var groupName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityWriteMessageBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.groupName = this.bundle.getString(Constant.Key.GROUP_NAME, "")
        this.binding.apply {
            this.textGroupName.text = "Untuk $groupName"
            GeneralHelper.makeClickable(this@WriteMessageActivity, this.exfabSend)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.exfabSend) {
            this.send()
        }
    }

    /*    private fun send() {
            DatabaseHelper.Firebase.getInboxReference(this.groupName).removeValue()
        }*/
    private fun send() {
        var title = ""
        var message = ""

        val authId = AuthHelper.getUid()
        var sender = ""

        this.binding.apply {
            title = this.inputTitle.text.toString()
            message = this.inputMessage.text.toString()
        }

        if (title.isEmpty() || message.isEmpty()) {
            BottomSheets.error(
                this,
                getString(R.string.something_wrong),
                getString(R.string.make_sure_fill_was_filled),
                true
            )
            return
        }

        val messages = ArrayList<MessageFirebase>()

        var isCanContinue = true
        var index =0

        Log.d(WriteMessageActivity::class.java.simpleName, "Start Getting User Information....")
        DatabaseHelper
            .Firebase
            .getAccountReference(authId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserFirebase::class.java)
                    sender = user!!.name
                    Log.d(WriteMessageActivity::class.java.simpleName, "Getting User Information Success. Sender Name: $sender")
                    val messageModel = MessageFirebase(title, message, sender, System.currentTimeMillis(), Constant.MessageType.REGULAR)
                    DatabaseHelper
                        .Firebase
                        .getInboxReference(this@WriteMessageActivity.groupName)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                Log.d("Loop", "${index++}")
                                for (data in snapshot.children) {
                                    val messageChildren = data.getValue(MessageFirebase::class.java)
                                    messages.add(messageChildren!!)
                                }

                                messages.add(messageModel)

                                if (isCanContinue) {
                                    DatabaseHelper
                                        .Firebase
                                        .getInboxReference(this@WriteMessageActivity.groupName)
                                        .setValue(messages)
                                        .addOnSuccessListener {
                                            val bundle = Bundle()
                                            bundle.putString(
                                                Constant.Key.SUCCESS_TITLE,
                                                getString(R.string.notification_sended)
                                            )
                                            MoveTo.success(this@WriteMessageActivity, bundle, true)
                                        }
                                }
                                isCanContinue = false

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }
}