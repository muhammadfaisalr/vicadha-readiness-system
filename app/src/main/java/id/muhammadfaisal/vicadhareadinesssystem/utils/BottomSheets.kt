package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.bottomsheet.BottomSheetChildGroup
import id.muhammadfaisal.vicadhareadinesssystem.bottomsheet.BottomSheetError
import id.muhammadfaisal.vicadhareadinesssystem.bottomsheet.BottomSheetSendMessage

class BottomSheets {
    companion object {
        fun sendMessage(context: Context, groupName: String) {
            context as AppCompatActivity
            val bottomSheet = BottomSheetSendMessage(groupName)
            bottomSheet.show(context.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun childGroup(context: Context, groupName: String) {
            context as AppCompatActivity
            val bottomSheet = BottomSheetChildGroup(groupName)
            bottomSheet.show(context.supportFragmentManager, BottomSheets::class.java.simpleName)
        }

        fun error(context: Context, title: String, message: String?, isCancelable: Boolean) {
            context as AppCompatActivity
            val bottomSheets = BottomSheetError(title, message)
            bottomSheets.isCancelable = isCancelable
            bottomSheets.show(context.supportFragmentManager, BottomSheets::class.java.simpleName)
        }
    }
}