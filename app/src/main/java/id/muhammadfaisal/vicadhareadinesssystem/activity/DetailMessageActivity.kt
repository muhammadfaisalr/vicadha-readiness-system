package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailMessageBinding

class DetailMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMessageBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {

        }
    }
}