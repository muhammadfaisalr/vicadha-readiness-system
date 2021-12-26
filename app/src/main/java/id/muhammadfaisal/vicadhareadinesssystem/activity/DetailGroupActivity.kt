package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailGroupBinding

class DetailGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailGroupBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.apply {

        }
    }
}