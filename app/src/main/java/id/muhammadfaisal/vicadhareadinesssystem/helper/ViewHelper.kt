package id.muhammadfaisal.vicadhareadinesssystem.helper

import android.content.Context
import android.view.View

class ViewHelper {
    companion object {
        fun hide(vararg views: View) {
            for (i in views) {
                i.visibility = View.GONE
            }
        }

        fun disable(vararg views: View) {
            for (i in views) {
                i.isEnabled = false
            }
        }
    }
}