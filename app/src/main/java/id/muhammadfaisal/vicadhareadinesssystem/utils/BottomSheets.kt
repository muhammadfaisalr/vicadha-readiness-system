package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.bottomsheet.BottomSheetSendMessage

class BottomSheets {
    companion object {
        fun sendMessage(context: Context, bundle: Bundle?) {
            context as AppCompatActivity
            val bottomSheet = BottomSheetSendMessage()
            if (bundle != null) bottomSheet.arguments = bundle
            bottomSheet.show(context.supportFragmentManager, BottomSheets::class.java.simpleName)
        }
    }
}