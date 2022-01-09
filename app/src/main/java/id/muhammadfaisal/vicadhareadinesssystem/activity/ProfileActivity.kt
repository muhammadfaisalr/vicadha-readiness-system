package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.GroupAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityMainBinding
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityProfileBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityProfileBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
    }

    override fun onClick(p0: View?) {

    }
}