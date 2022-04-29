package com.lelyuh.testcarapp.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Custom start screen with app logo
 *
 * @author Leliukh Aleksandr
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(ManufacturerListActivity.newIntent(this))
        finish()
    }
}
