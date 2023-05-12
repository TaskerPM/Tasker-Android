package com.tasker.android.app

import android.util.Log
import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.data.api.ServerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
//
//    @Inject
//    lateinit var serverApi: ServerApi
//
//    @Test
//    fun serverTest() {
//        CoroutineScope(Dispatchers.IO).launch {
//            kotlin.runCatching {
//                serverApi.postSmsSend(
//                    req = SmsSendRequest("01025528507")
//                )
//            }
//                .onSuccess {
//                    Log.d("Response Success", it.toString())
//                }
//                .onFailure {
//                    Log.d("Response Failed", it.toString())
//                }
//        }
//    }
}