package id.muhammadfaisal.vicadhareadinesssystem.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.vicadhareadinesssystem.R
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentBottomSheetSendMessageBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper
import id.muhammadfaisal.vicadhareadinesssystem.utils.MoveTo

class BottomSheetSendMessage : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentBottomSheetSendMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentBottomSheetSendMessageBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.binding.apply {
            GeneralHelper.makeClickable(this@BottomSheetSendMessage, this.linearDanger, this.linearRegular)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.linearDanger) {
            MoveTo.sendNotification(requireContext(), null, false)
        } else {
            MoveTo.regularMessage(requireContext(), null, false)
        }
    }
}