package com.lelyuh.testcarapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.lelyuh.testcarapp.CarApplication
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.databinding.CarSummaryActivityBinding
import com.lelyuh.testcarapp.models.presentation.CarModel

/**
 * Activity for represent summary info of given car model
 * Supports both portrait and landscape orientation with different designs and entry/exit animation of operation area
 *
 * @author Leliukh Aleksandr
 */
class CarSummaryActivity : AppCompatActivity() {

    private lateinit var binding: CarSummaryActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CarApplication).carComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = CarSummaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        runStartAnimation()
    }

    override fun onBackPressed() {
        with(binding) {
            summaryContainer.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.bottom_container_down_animation)
            )
            root.postDelayed(
                {
                    summaryContainer.isGone = true
                    finish()
                },
                DELAY_CLOSE_SCREEN
            )
        }
    }

    private fun initView() {
        val carModel = intent.getSerializableExtra(CAR_MODEL_KEY) as CarModel
        with(binding) {
            carLogoView.setImageResource(carModel.manufacturerIconResId)
            manufacturerTextView.text = carModel.manufacturerName
            modelTextView.text = getString(R.string.car_summary_model_format, carModel.carModelType)
            yearTextView.text = getString(R.string.car_summary_year_format, carModel.carBuildDate)
        }
    }

    private fun runStartAnimation() {
        with(binding) {
            root.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.background_enter_animation)
            )
            summaryContainer.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.bottom_container_up_animation)
            )
        }
    }

    companion object {
        private const val CAR_MODEL_KEY = "carModelKey"
        private const val DELAY_CLOSE_SCREEN = 500L

        fun newIntent(context: Context, model: CarModel) =
            Intent(context, CarSummaryActivity::class.java).apply {
                putExtra(CAR_MODEL_KEY, model)
            }
    }
}
