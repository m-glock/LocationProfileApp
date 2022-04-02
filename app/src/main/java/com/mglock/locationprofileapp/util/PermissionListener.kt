package com.mglock.locationprofileapp.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionListener(private val activityContext: Context, val onPermissionsGranted : () -> Unit): MultiplePermissionsListener {

    //TODO properly handle all possible actions from the user (deny, deny&don't ask again etc.)
    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if(!report!!.areAllPermissionsGranted()){
            AlertDialog.Builder(activityContext)
                .setTitle("Permissions required")
                .setMessage("If you want to use this feature, you have to grant the permission.")
                .setPositiveButton(
                    "Edit permissions"
                ) { _, _ ->
                    //TODO
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", activityContext.packageName, null)
                    activityContext.startActivity(intent)
                }
                .setNegativeButton("I'm fine", null)
                .show()
        } else {
            onPermissionsGranted()
        }
    }

    // This method will be called when the user rejects a permission request
    // You must display a dialog box that explains to the user why the application needs this permission
    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }
}