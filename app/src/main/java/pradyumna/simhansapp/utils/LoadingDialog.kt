package pradyumna.simhansapp.utils

import android.app.Activity
import android.app.AlertDialog
import pradyumna.simhansapp.R

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading() {
        /**set View*/
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.progress_dialog, null)

        /**set Dialog*/
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isdialog.show()
    }

    fun isDismiss() {
        isdialog.dismiss()
    }
}