package gizahost.alkora.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import gizahost.alkora.R

object Dialogs {
    fun createProgressBarDialog(
        mContext: Context?,
        text: String?
    ): Dialog {
        val mDialog: Dialog
        mDialog = Dialog(mContext!!, R.style.Theme_Dialog)
        mDialog.setContentView(R.layout.dialog_progress_bar)

        //find Views
//        val rotateLoading: RotateLoading = mDialog.findViewById<View>(R.id.rotateloading) as RotateLoading
//        rotateLoading.start()
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCancelable(false)
        return mDialog
    }
}