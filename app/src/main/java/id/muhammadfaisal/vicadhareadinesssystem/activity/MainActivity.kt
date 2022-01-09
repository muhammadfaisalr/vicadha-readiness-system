package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.adapter.GroupAdapter
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityMainBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)

        this.binding.apply {
            this.recyclerGroup.layoutManager = GridLayoutManager(this@MainActivity, 2)
            this.recyclerGroup.adapter = GroupAdapter(this@MainActivity, DatabaseHelper.RoomDb.groupDao(this@MainActivity).getAll())


            GeneralHelper.makeClickable(this@MainActivity, this.textProfile, this.buttonAddAdmin)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.textProfile) {
            MoveTo.profile(this, null, false)
        }

        if (p0 == this.binding.buttonAddAdmin) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.FOR_GROUP, getString(R.string.super_admin))
            bundle.putInt(Constant.Key.ROLE_ID, Constant.Role.SUPER_ADMIN)

            MoveTo.addMember(this, bundle, false)
        }
    }
}