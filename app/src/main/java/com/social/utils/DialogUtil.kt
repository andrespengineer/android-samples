package com.social.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.social.R
import androidx.annotation.LayoutRes

object DialogUtil {

    private var customAlertDialog: AlertDialog? = null

    fun createCustomDialog(context: Context, @LayoutRes layoutId: Int, message: String, onClickYes: (() -> Unit)? = null, onClickNo: (() -> Unit)? = null) {

        val factory: LayoutInflater = LayoutInflater.from(context)
        val view: View = factory.inflate(layoutId, null)
        customAlertDialog = AlertDialog
                .Builder(context)
                .create()
        customAlertDialog?.setView(view)
        (view.findViewById<View>(R.id.tvCustomDialogMessage) as TextView).text = (message)
        view.findViewById<View>(R.id.btnOk).setOnClickListener {
            onClickYes?.invoke()
            customAlertDialog?.dismiss()
            customAlertDialog = null
        }
        view.findViewById<View>(R.id.btnCancel).setOnClickListener {
            onClickNo?.invoke()
            customAlertDialog?.dismiss()
            customAlertDialog = null
        }

        customAlertDialog?.window?.setBackgroundDrawableResource(R.drawable.shape_corner_radius_popup_suggest_song)

        customAlertDialog?.show()
    }
}