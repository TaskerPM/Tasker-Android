package com.tasker.android.app.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tasker.android.app.R
import com.tasker.android.app.databinding.ActivityMainBinding
import com.tasker.android.common.constants.Constants
import com.tasker.android.login.activity.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        makeEdgeScreen()
        setupNavigation()
        loginTest()
    }

    private fun loginTest() {
        when (intent.getStringExtra("from") ?: "n") {
            "n" -> {
                startLoginActivity()
            }
            Constants.LAUNCH_FROM_LOGIN_TO_MAIN -> {

            }
        }
    }

    private fun makeEdgeScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.tasker.android.home.R.id.homeFragment -> {
                    binding.bnvMain.isVisible = true
                }
                com.tasker.android.collection.R.id.collectionFragment -> {
                    binding.bnvMain.isVisible = true
                }
                com.tasker.android.taskbook.R.id.taskbookFragment -> {
                    binding.bnvMain.isVisible = true
                }
                com.tasker.android.mypage.R.id.mypageFragment -> {
                    binding.bnvMain.isVisible = true
                }
                else -> binding.bnvMain.isVisible = false
            }
        }

        binding.bnvMain.setupWithNavController(navController)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}