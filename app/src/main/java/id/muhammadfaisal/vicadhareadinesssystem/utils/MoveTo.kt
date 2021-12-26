package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.activity.DetailGroupActivity
import id.muhammadfaisal.vicadhareadinesssystem.activity.MainActivity
import id.muhammadfaisal.vicadhareadinesssystem.helper.GeneralHelper

class MoveTo {
    companion object {
        fun home(context: Context, bundle: Bundle?, isForget: Boolean) {
            context as AppCompatActivity
            if (bundle != null) {
                GeneralHelper.move(context, MainActivity::class.java, bundle, isForget)
            }else{
                GeneralHelper.move(context, MainActivity::class.java, isForget)
            }
        }

        fun detailGroup(context: Context, bundle: Bundle?, isForget: Boolean) {
            context as AppCompatActivity
            if (bundle != null) {
                GeneralHelper.move(context, DetailGroupActivity::class.java, bundle, isForget)
            }else{
                GeneralHelper.move(context, DetailGroupActivity::class.java, isForget)
            }
        }
    }
}