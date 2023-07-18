package com.tasker.android.login.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.tasker.android.common.constants.Constants
import com.tasker.android.login.activity.LoginActivity

fun startMainActivity(context: Context, activity: FragmentActivity) {
    val intent = Intent()
    intent.setClassName(
        context,
        "com.tasker.android.app.view.MainActivity"
    )
    intent.putExtra("from", Constants.LAUNCH_FROM_LOGIN_TO_MAIN)
    activity.startActivity(intent)

    (activity as LoginActivity).finish()
}