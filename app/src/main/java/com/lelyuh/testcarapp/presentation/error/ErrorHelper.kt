package com.lelyuh.testcarapp.presentation.error

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.lelyuh.testcarapp.R

/**
 * Helper to show information about any errors on loading data
 *
 * @author Leliukh Aleksandr
 */
object ErrorHelper {

    fun showErrorDialog(activity: Activity, @StringRes buttonTextResId: Int = R.string.error_ok_button) {
        AlertDialog.Builder(activity)
            .setTitle(R.string.error_title)
            .setMessage(R.string.error_message)
            .setPositiveButton(buttonTextResId) { _, _ ->
                activity.finish()
            }
            .setCancelable(false)
            .show()
    }
}