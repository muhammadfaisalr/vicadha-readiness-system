package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.fragment.DocumentFragment
import id.muhammadfaisal.vicadhareadinesssystem.utils.Constant

class GeneralHelper {
    companion object {
        fun move(activity: AppCompatActivity, clazz: Class<*>, isForget: Boolean){
            activity.startActivity(Intent(activity, clazz))
            if (isForget){
                activity.finish()
            }
        }

        fun move(activity: AppCompatActivity, clazz: Class<*>, bundle: Bundle, isForget: Boolean){
            activity.startActivity(Intent(activity, clazz).putExtra(Constant.Key.BUNDLING, bundle))
            if (isForget){
                activity.finish()
            }
        }

        fun makeClickable(listener: View.OnClickListener, vararg views: View) {
            for (view in views){
                view.setOnClickListener(listener)
            }
        }

        fun textAvatar(s: String) : String{
            val splitted = s.split(" ")

            var strings = ""
            var index = 0

            try {
                if (splitted.isNotEmpty()) {
                    for (i in splitted){
                        if (index < 2){
                            strings += i[0].toString()
                        }
                        index += 1
                    }
                }else{
                    return s.substring(1)
                }
            }catch (e: Exception) {
                Log.e(GeneralHelper::class.java.simpleName, e.message.toString())
            }

            return strings
        }

        fun setAsGroupId(s: String): String {
            return s.replace(" ", "")
        }

        fun getRoleName(roleId: Int): String {
            return when (roleId) {
                Constant.Role.ADMIN -> {
                    Constant.RoleName.ADMIN
                }
                Constant.Role.SUPER_ADMIN -> {
                    Constant.RoleName.SUPER_ADMIN
                }
                else -> {
                    Constant.RoleName.MEMBER
                }
            }
        }

        fun getRoleId(roleName: String): Int {
            return when (roleName) {
                Constant.RoleName.ADMIN -> {
                    Constant.Role.ADMIN
                }
                Constant.RoleName.SUPER_ADMIN -> {
                    Constant.Role.SUPER_ADMIN
                }
                else -> {
                    Constant.Role.MEMBER
                }
            }
        }

        fun queryFormat(s: String): String {
            return "%$s%"
        }
    }
}