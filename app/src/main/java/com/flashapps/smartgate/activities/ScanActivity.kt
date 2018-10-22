package com.flashapps.smartgate.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.canelmas.let.*
import com.flashapps.smartgate.R
import com.flashapps.smartgate.extensions.snackbar
import com.flashapps.smartgate.helpers.ErrorHelper
import com.flashapps.smartgate.managers.PermissionManager
import com.flashapps.smartgate.managers.RestClient
import com.flashapps.smartgate.models.Credential
import com.google.gson.JsonElement
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.orhanobut.logger.Logger
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_scan.*
import retrofit2.Call
import retrofit2.Callback


/**
 * Created by dietervaesen on 23/01/18.
 */
class ScanActivity : BaseActivity(), RuntimePermissionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if(realm.where<Credential>().count() > 0){
            goToMainFlow()
        }
        else {
            setUpBarCodescanner()
        }
    }


    var lastBarCode: String? = null


    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            val result = result.text
            if (result == null || result == lastBarCode) {
                // Prevent duplicate scans
                return
            } else {
                lastBarCode = result
                beepManager?.playBeepSoundAndVibrate();
                if (result.contains("qru=")) {
                    Log.i("good code scan result", result)
                    val filter = "qru="
                    val qruIndex = result.indexOf(filter, 0, true) + filter.length
                    Log.i("index of qru ", "" + qruIndex)

                    val qruAuthentication = result.substring(qruIndex, result.length)//tot - niet tot en met --> dus ik mag length van string gebruiken als positie
                    authenticate(qruAuthentication);

                } else {
                    Log.i("bad code scan result", result)
                }
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    fun authenticate(qruAuthenticate: String) {
        RestClient.getApiService(applicationContext, qruAuthenticate).authenticate().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: retrofit2.Response<JsonElement>) {
                if (response.code() == 200) {
                    val responseBody = response.body()
                    when (responseBody.asJsonObject.has("Credentials")) {
                        true -> {
                            Logger.d(response.body().toString())
                            val credentials = responseBody.asJsonObject.get("Credentials")
                            realm.executeTransaction {
                               it.createOrUpdateAllFromJson(Credential::class.java, credentials.toString())
                            }

                            goToMainFlow()
                        }
                        else -> snackbar("Er werden geen credentials gevonden")
                    }
                    Logger.d(response.body().toString())
                } else {
                    val error = ErrorHelper.parseError(response)
                    snackbar(error.getMessage())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                ErrorHelper.processError(t, this@ScanActivity)
            }
        })
    }


    fun goToMainFlow() {
        val mainIntent = Intent(ScanActivity@ this, MainActivity::class.java);
        startActivity(mainIntent);
        finish()
    }

    private var beepManager: BeepManager? = null

    @AskPermission(Manifest.permission.CAMERA)
    private fun setUpBarCodescanner() {
        barcodeScanner.decodeContinuous(callback)
        beepManager = BeepManager(this)
    }


    override fun onResume() {
        super.onResume()
        barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScanner.pause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Let.handle(this, requestCode, permissions, grantResults)
    }


    override fun onShowPermissionRationale(permissionList: List<String>, permissionRequest: RuntimePermissionRequest) {
        PermissionManager.getRuntimePermissionListener(this).onShowPermissionRationale(permissionList, permissionRequest)
    }

    override fun onPermissionDenied(deniedPermissionList: List<DeniedPermission>) {
        PermissionManager.getRuntimePermissionListener(this).onPermissionDenied(deniedPermissionList)
    }

}