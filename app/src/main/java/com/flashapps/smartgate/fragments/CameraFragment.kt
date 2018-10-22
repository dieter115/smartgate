package com.flashapps.smartgate.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.flashapps.smartgate.App
import com.flashapps.smartgate.R
import com.flashapps.smartgate.activities.BaseActivity
import com.flashapps.smartgate.extensions.snackbar
import com.flashapps.smartgate.helpers.ErrorHelper
import com.flashapps.smartgate.managers.RestClient
import com.flashapps.smartgate.models.Credential
import com.google.gson.JsonObject
import com.pedro.vlc.VlcListener
import com.pedro.vlc.VlcVideoLibrary
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_camera.*
import retrofit2.Call
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment(), VlcListener {
    private var mCameraCredentialId: String? = null
    private var mParam2: String? = null
    private lateinit var currentCameraCredential: Credential
    private lateinit var surfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mCameraCredentialId = arguments!!.getString(ARG_CAMERA_CREDENTIAL_ID)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = activity as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    /*var tagPattern = Pattern.compile("rtsp://\\w+?:\\w+?@")//+ os 1 or more * is 0 or more " *" watch space (\w+?:\w+?)/(\w+?)/(\w+?)/(\w+?)*/

    private lateinit var vlcVideoLibrary: VlcVideoLibrary

    private val options = arrayOf(":fullscreen")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentCameraCredential = mActivity.realm.where<Credential>().findFirst()!!

        surfaceHolder = mediaPlayerSurface.holder

        vlcVideoLibrary = VlcVideoLibrary(context, this, mediaPlayerSurface)
        /*vlcVideoLibrary.setOptions(Arrays.asList(options))*/

        btnPlay.setOnClickListener {
            if (vlcVideoLibrary.isPlaying) {
                vlcVideoLibrary.stop()
                btnPlay.setText(R.string.start_camera)
            }
            else {
                vlcVideoLibrary.play(currentCameraCredential.url)
            }
        }

        btnToggleGate.setOnClickListener {
            toggleGate();
        }
    }

    private fun toggleGate() {
        RestClient.getApiService(App.getContext(), "${currentCameraCredential.username}:${currentCameraCredential.password}").toggleGate(currentCameraCredential.gate_id).enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>) {
                if (response.code() == 200) {
                    snackbar("Gate toggled")
                } else {
                    snackbar(ErrorHelper.parseError(response).message)
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                snackbar(ErrorHelper.processError(t, mActivity).toString())
            }


        })
    }

    companion object {
        private val ARG_CAMERA_CREDENTIAL_ID = "cameracredentialid"
        private val ARG_PARAM2 = "param2"
        fun newInstance(cameraCredentialId: String, param2: String): CameraFragment {
            val fragment = CameraFragment()
            val args = Bundle()
            args.putString(ARG_CAMERA_CREDENTIAL_ID, cameraCredentialId)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onComplete() {
        snackbar("Playing")
        btnPlay.setText(R.string.stop_camera)
    }

    override fun onError() {
        snackbar("Error, make sure your endpoint is correct")
        vlcVideoLibrary.stop();
    }

}
