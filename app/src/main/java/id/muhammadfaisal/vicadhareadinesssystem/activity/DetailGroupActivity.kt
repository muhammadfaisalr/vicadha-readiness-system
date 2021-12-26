package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailGroupBinding
import id.muhammadfaisal.vicadhareadinesssystem.fragment.InboxFragment
import id.muhammadfaisal.vicadhareadinesssystem.fragment.MemberFragment
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper

class DetailGroupActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityDetailGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailGroupBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.changeFragment(0)

        this.binding.apply {
            this.tabLayout.addOnTabSelectedListener(this@DetailGroupActivity)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab!!.text == resources.getString(R.string.member)) {
            this@DetailGroupActivity.changeFragment(0)
        } else {
            this@DetailGroupActivity.changeFragment(1)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    private fun changeFragment(i: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        if (i == 0) {
            transaction.replace(R.id.frameLayout, MemberFragment())
        } else {
            transaction.replace(R.id.frameLayout, InboxFragment())
        }

        transaction.commit()
    }
}