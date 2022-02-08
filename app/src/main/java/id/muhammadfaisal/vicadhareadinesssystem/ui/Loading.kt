package id.muhammadfaisal.vicadhareadinesssystem.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import id.muhammadfaisal.vicadhareadinesssystem.R

@SuppressLint("InflateParams", "UseCompatLoadingForDrawables")
class Loading(context: Context) : AlertDialog(context) {

    init {
        super.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(context).inflate(R.layout.loading, null)
    }

    override fun show() {
        super.show()
        super.setContentView(R.layout.loading)
    }


    override fun cancel() {
        super.cancel()
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
    }
}