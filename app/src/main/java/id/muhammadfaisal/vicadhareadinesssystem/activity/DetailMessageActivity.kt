package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailMessageBinding
import id.muhammadfaisal.vicadhareadinesssystem.firebase.model.MessageFirebase
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import java.text.SimpleDateFormat

class DetailMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMessageBinding
    private lateinit var message: MessageFirebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMessageBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.extract()
        val formatter = SimpleDateFormat("HH:mm EEE, dd/MM/yyyy")
        this.binding.apply {
            val message = message
            this.textTitle.text = message.title
            this.textSubtitle.text = message.sender
            this.textContent.text = message.message
            this.textDate.text = formatter.format(message.date)
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)
        this.message = bundle!!.getSerializable(Constant.Key.MESSAGE) as MessageFirebase
    }
}