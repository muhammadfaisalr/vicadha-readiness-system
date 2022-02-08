package id.muhammadfaisal.vicadhareadinesssystem.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentBottomSheetSendMessageBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.DatabaseHelper
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo
import id.muhammadfaisal.vicadhareadinesssystem.utils.SharedPreferences

class BottomSheetSendMessage(var groupName: String) : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentBottomSheetSendMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentBottomSheetSendMessageBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val groupDao = DatabaseHelper.RoomDb.groupDao(requireContext())
        val userDao = DatabaseHelper.RoomDb.userDao(requireContext())
        val email = SharedPreferences.get(requireContext(), Constant.Key.USER_EMAIL)
        val user = userDao.getByEmail(email.toString())

        if (user.groupName!!.isNotEmpty()) {
            Log.d(BottomSheetSendMessage::class.java.simpleName, "${user.name} Inside ${user.groupName} Group")
        }

        val isCanSendDangerMessage = if (user.groupName!!.isNotEmpty()) groupDao.get(user.groupName!!)!!.isCanSendDangerMessage else true

        Log.d(BottomSheetSendMessage::class.java.simpleName, "${this.groupName} Is Can Send Danger Message ? ${isCanSendDangerMessage}")
        if (!isCanSendDangerMessage) {
            this.binding.linearDanger.visibility = View.GONE
        }

        this.binding.apply {
            GeneralHelper.makeClickable(this@BottomSheetSendMessage, this.linearDanger, this.linearRegular)
        }
    }

    override fun onClick(p0: View?) {
        val bundle = Bundle()
        bundle.putString(Constant.Key.GROUP_NAME, groupName)
        if (p0 == this.binding.linearDanger) {
            MoveTo.sendNotification(requireContext(), bundle, false)
        } else {
            MoveTo.regularMessage(requireContext(), bundle, false)
        }
    }
}