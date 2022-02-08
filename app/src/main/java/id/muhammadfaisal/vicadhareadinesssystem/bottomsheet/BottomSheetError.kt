package id.muhammadfaisal.vicadhareadinesssystem.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentBottomSheetErrorBinding
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper

class BottomSheetError(var title: String, var message: String?) : BottomSheetDialogFragment(),
    View.OnClickListener {

    private lateinit var binding: FragmentBottomSheetErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentBottomSheetErrorBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.binding.apply {
            this.textTitle.text = title

            if (message != null) {
                this.textDescription.visibility = View.VISIBLE
                this.textDescription.text = message
            }

            GeneralHelper.makeClickable(this@BottomSheetError, this.buttonClose)
        }

    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonClose) {
            this.dismiss()
        }
    }
}