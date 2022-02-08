package id.muhammadfaisal.vicadhareadinesssystem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayout
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.ActivityDetailGroupBinding
import id.muhammadfaisal.vicadhareadinesssystem.fragment.DocumentFragment
import id.muhammadfaisal.vicadhareadinesssystem.fragment.InboxFragment
import id.muhammadfaisal.vicadhareadinesssystem.fragment.MemberFragment
import id.muhammadfaisal.vicadhareadinesssystem.fragment.UnityFragment
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class DetailGroupActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener,
    View.OnClickListener {

    private lateinit var binding: ActivityDetailGroupBinding

    private lateinit var bundle: Bundle
    private lateinit var groupName: String

    private var isFromNotification = true

    private var roleId = Constant.Role.SUPER_ADMIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailGroupBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        this.bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)!!
        this.groupName = this.bundle.getString(Constant.Key.GROUP_NAME, "")
        this.isFromNotification = this.bundle.getBoolean(Constant.Key.IS_FROM_NOTIFICATION, false)

        Log.d(DetailGroupActivity::class.java.simpleName, "Is From Notification? [$isFromNotification]")


        this.roleId = SharedPreferences.get(this, Constant.Key.ROLE_ID) as Int
        val hasChild = DatabaseHelper.RoomDb.groupDao(binding.root.context).getChildren(groupName).isNotEmpty()

        if (!hasChild) {
            this.binding.apply {
                this.tabLayout.getTabAt(3)!!.view.visibility = View.GONE
            }
        }

        when (this.roleId) {
            Constant.Role.MEMBER -> {
                this.binding.apply {
                    this.imageLeft.setImageResource(R.drawable.ic_outline_account_circle_24)
                    this.buttonAddAdmin.visibility = View.GONE
                    this.tabLayout.getTabAt(0)!!.view.visibility = View.GONE
                }

                if (isFromNotification) {
                    this.changeFragment(3)
                }else{
                    this.changeFragment(1)
                }

            }
            Constant.Role.ADMIN -> {
                this.binding.apply {
                    this.imageLeft.setImageResource(R.drawable.ic_outline_account_circle_24)
                    this.buttonAddAdmin.visibility = View.GONE
                }

                if (isFromNotification) {
                    this.changeFragment(3)
                }else{
                    this.changeFragment(0)
                }
            }
            else -> {
                if (isFromNotification) {
                    this.changeFragment(3)
                }else{
                    this.changeFragment(0)
                }
            }
        }

        val userDao = DatabaseHelper.RoomDb.userDao(this)
        val groupDao = DatabaseHelper.RoomDb.groupDao(this)

        val users = userDao.getAllByGroupName(this.groupName)
        val group = groupDao.get(this.groupName)!!

        this.binding.apply {
            this.textGroupName.text = this@DetailGroupActivity.groupName
            this.textTotalMember.text = "${users.size} Anggota"
            this.imageLogo.setImageResource(group.image)

            this.tabLayout.addOnTabSelectedListener(this@DetailGroupActivity)

            GeneralHelper.makeClickable(this@DetailGroupActivity, this.imageLeft, this.buttonAddAdmin)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when {
            tab!!.text == resources.getString(R.string.member) -> {
                this@DetailGroupActivity.changeFragment(0)
            }
            tab.text == getString(R.string.inbox) -> {
                this@DetailGroupActivity.changeFragment(1)
            }
            tab.text == getString(R.string.unity) -> {
                this@DetailGroupActivity.changeFragment(3)
            }
            else -> {
                this@DetailGroupActivity.changeFragment(2)
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    private fun changeFragment(i: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (i) {
            0 -> {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, this.groupName)

                val fragment = MemberFragment()
                fragment.arguments = bundle
                transaction.replace(R.id.frameLayout, fragment)
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, this.groupName)
                val fragment = InboxFragment()
                fragment.arguments = bundle

                transaction.replace(R.id.frameLayout, fragment)
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, this.groupName)

                val fragment = DocumentFragment()
                fragment.arguments = bundle

                transaction.replace(R.id.frameLayout, fragment)
            }
            3 -> {
                val bundle = Bundle()
                bundle.putString(Constant.Key.GROUP_NAME, this.groupName)

                val fragment = UnityFragment()
                fragment.arguments = bundle

                transaction.replace(R.id.frameLayout, fragment)
            }
        }

        transaction.commit()
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.imageLeft) {
            if (this.roleId == Constant.Role.SUPER_ADMIN) {
                this.finish()
            }else{
                MoveTo.profile(this, null, false)
            }
        }else if (p0 == this.binding.buttonAddAdmin) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.FOR_GROUP, this.groupName)
            bundle.putInt(Constant.Key.ROLE_ID, Constant.Role.ADMIN)
            MoveTo.addMember(this, bundle, false)
        }
    }
}