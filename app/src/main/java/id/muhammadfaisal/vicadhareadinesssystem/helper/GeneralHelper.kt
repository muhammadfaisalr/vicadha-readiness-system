package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.fragment.MemberFragment
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
    }
}