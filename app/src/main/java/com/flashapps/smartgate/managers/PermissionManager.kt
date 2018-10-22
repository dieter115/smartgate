package com.flashapps.smartgate.managers

import android.app.Activity
import android.support.v4.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.canelmas.let.DeniedPermission
import com.canelmas.let.RuntimePermissionListener
import com.canelmas.let.RuntimePermissionRequest
import com.flashapps.smartgate.App
import com.flashapps.smartgate.R

/**
 * Created by dietervaesen on 23/01/18.
 */
class PermissionManager {

    //static way of calling method
    companion object {
        fun getRuntimePermissionListener(mActivity: Activity): RuntimePermissionListener {
            return object : RuntimePermissionListener {
                override fun onShowPermissionRationale(permissionList: List<String>, permissionRequest: RuntimePermissionRequest) {
                    permissionRequest.retry()
                }


                override fun onPermissionDenied(deniedPermissionList: List<DeniedPermission>) {
                    var denied = ""
                    for (deniedPermission in deniedPermissionList) {
                        denied += deniedPermission.permission + "\n"
                        if (deniedPermission.isNeverAskAgainChecked) {
                            //show dialog to go to settings & manually allow the permission
                            val permissionDialog = MaterialDialog.Builder(mActivity)
                                    .title("Permissions")
                                    .theme(Theme.LIGHT)
                                    .positiveColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary))
                                    .negativeColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary))
                                    .content("Smartgate needs your permissions")
                                    .positiveText("Go to settings")
                                    .negativeText(android.R.string.cancel)
                                    .onPositive(MaterialDialog.SingleButtonCallback{
                                        dialog, which ->
                                        DialogManager.startInstalledAppDetailsActivity(mActivity)
                                        dialog.dismiss()
                                    })
                                    .onNegative(MaterialDialog.SingleButtonCallback({
                                        dialog, which -> dialog.dismiss();
                                    }))
                                    .build()
                            if (!permissionDialog.isShowing()) {
                                permissionDialog.show()
                            }
                        }
                    }
                }
            }
        }
    }

}

