package com.flashapps.smartgate.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.ContextCompat

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.flashapps.smartgate.App
import com.flashapps.smartgate.R

/**
 * Created by dietervaesen on 24/01/18.
 */
val TAKE_PICTURE = 1
val PICK_IMAGE = 2

fun Activity.getLoading(content: String): MaterialDialog {
    return MaterialDialog.Builder(this)
            .content(content)
            .theme(Theme.LIGHT)
            .progress(true, 0)
            .cancelable(false)
            .titleColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary))
            .contentColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary))
            .widgetColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary))
            .build()
}

fun Activity.startInstalledAppDetailsActivity() {
    if (baseContext == null) {
        return
    }
    val i = Intent()
    i.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    i.addCategory(Intent.CATEGORY_DEFAULT)
    i.data = Uri.parse("package:" + baseContext.packageName)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    baseContext.startActivity(i)
}


fun Activity.takePhoto() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, TAKE_PICTURE)
}

fun Activity.pickPhoto() {
    val getIntent = Intent(Intent.ACTION_GET_CONTENT)// ALLEEN
    getIntent.type = "image/*"
    val chooserIntent = Intent.createChooser(getIntent, "Select Image")
    startActivityForResult(chooserIntent, PICK_IMAGE)
}