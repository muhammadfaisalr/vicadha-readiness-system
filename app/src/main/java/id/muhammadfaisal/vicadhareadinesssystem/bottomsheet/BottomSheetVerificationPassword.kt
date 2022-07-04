package id.muhammadfaisal.vicadhareadinesssystem.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.vicadhareadinesssystem.databinding.FragmentBottomSheetVerificationPasswordBinding
import id.muhammadfaisal.vicadhareadinesssystem.listener.ClickListener

class BottomSheetVerificationPassword(private val clickListener: ClickListener) : BottomSheetDialogFragment(),
    View.OnClickListener {

    private lateinit var binding: FragmentBottomSheetVerificationPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentBottomSheetVerificationPasswordBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.apply {
            this.buttonVerification.setOnClickListener(this@BottomSheetVerificationPassword)
            this.imageBack.setOnClickListener(this@BottomSheetVerificationPassword)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonVerification) {
            val password = this.binding.inputPassword.text

            if (password != null) {
                if (password.isNotEmpty()) {
                    clickListener.verification(password = password.toString())
                    this.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Password Belum Di Masukkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Password Belum Di Masukkan", Toast.LENGTH_SHORT).show()
            }
        } else {
            this.dismiss()
        }
    }
}