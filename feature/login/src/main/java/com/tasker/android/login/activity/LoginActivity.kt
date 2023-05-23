package com.tasker.android.login.activity

import android.app.Activity
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.tasker.android.login.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                smsVerificationRetriever,
                intentFilter,
                SmsRetriever.SEND_PERMISSION,
                null
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SMS_CONSENT_REQUEST -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val msg = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

                } else {
                    Log.d("SMS Retriever err", "activity err")
                }
            }
        }
    }

    private val SMS_CONSENT_REQUEST = 1
    private val smsVerificationRetriever = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val consentIntent =
                            extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            ActivityCompat.startActivityForResult(
                                this@LoginActivity,
                                consentIntent!!,
                                SMS_CONSENT_REQUEST,
                                null
                            )
                        } catch (e: ActivityNotFoundException) {
                            Log.d("SMS Retriever Err", e.toString())
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        Log.d("SMS Retriever Err", "timeout")
                    }
                }
            }
        }
    }


}
