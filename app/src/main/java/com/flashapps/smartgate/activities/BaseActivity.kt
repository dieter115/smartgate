package com.flashapps.smartgate.activities

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import io.realm.Realm

/**
 * Created by dietervaesen on 23/01/18.
 */
open class BaseActivity : AppCompatActivity() {
    private var dialog: MaterialDialog? = null
    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }


    fun showProgress(resIdTitle: Int, resIdContent: Int, onCancelListener: DialogInterface.OnCancelListener) {
        dialog = MaterialDialog.Builder(this)
                .title(resIdTitle)
                .content(resIdContent)
                .autoDismiss(false)
                .progress(true, 0)
                .cancelable(true)
                .cancelListener(onCancelListener)
                .show()
    }

    fun hideProgress() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
        dialog = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {
        super.onStop()
    }
}
