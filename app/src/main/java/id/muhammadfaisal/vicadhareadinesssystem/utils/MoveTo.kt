package id.muhammadfaisal.vicadhareadinesssystem.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.vicadhareadinesssystem.activity.*
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

        fun sendNotification(context: Context, bundle: Bundle?, isForget: Boolean) {
            context as AppCompatActivity
            if (bundle != null) {
                GeneralHelper.move(context, SendNotificationActivity::class.java, bundle, isForget)
            }else{
                GeneralHelper.move(context, SendNotificationActivity::class.java, isForget)
            }
        }


        fun regularMessage(context: Context, bundle: Bundle?, isForget: Boolean) {
            context as AppCompatActivity
            if (bundle != null) {
                GeneralHelper.move(context, WriteMessageActivity::class.java, bundle, isForget)
            }else{
                GeneralHelper.move(context, WriteMessageActivity::class.java, isForget)
            }
        }

        fun detailMessage(context: Context, bundle: Bundle?, isForget: Boolean) {
            context as AppCompatActivity
            if (bundle != null) {
                GeneralHelper.move(context, DetailMessageActivity::class.java, bundle, isForget)
            }else{
                GeneralHelper.move(context, DetailMessageActivity::class.java, isForget)
            }
        }

        fun success(context: Context, bundle: Bundle, isForget: Boolean) {
            context as AppCompatActivity
            GeneralHelper.move(context, SuccessActivity::class.java, bundle, isForget)
        }
    }
}