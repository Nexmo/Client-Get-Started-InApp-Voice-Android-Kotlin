package com.nexmo.client.getstarted.calls

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.nexmo.client.request_listener.NexmoApiError

abstract class BaseActivity : AppCompatActivity() {

    private val CALL_PERMISSIONS_REQ = 121

    override fun onStart() {
        super.onStart()
        if (doesNeedCallPermissions()) {
            requestCallPermissions()
        }
    }

    fun doesNeedCallPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (perm in callsPermissions) {
                if (ActivityCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }
        }
        return false
    }

    fun requestCallPermissions() {
        ActivityCompat.requestPermissions(this, callsPermissions, CALL_PERMISSIONS_REQ)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CALL_PERMISSIONS_REQ) {
            requestCallPermissions()
        }
    }

    companion object {
        private val callsPermissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.PROCESS_OUTGOING_CALLS)
    }

    fun notifyError(nexmoApiError: NexmoApiError) {
        Log.e(TAG, nexmoApiError.message)
        Toast.makeText(this, TAG, Toast.LENGTH_LONG).show()
    }
}

