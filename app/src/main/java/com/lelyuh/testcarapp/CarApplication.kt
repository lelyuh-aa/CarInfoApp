package com.lelyuh.testcarapp

import android.app.Application
import com.lelyuh.testcarapp.di.CarComponent
import com.lelyuh.testcarapp.di.DaggerCarComponent

class CarApplication : Application() {
    val carComponent: CarComponent = DaggerCarComponent.create()
}