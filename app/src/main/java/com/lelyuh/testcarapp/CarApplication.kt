package com.lelyuh.testcarapp

import android.app.Application
import com.lelyuh.testcarapp.di.CarComponent
import com.lelyuh.testcarapp.di.DaggerCarComponent

/**
 * Custom application for create Dagger component to use DI framework on app screens
 *
 * @author Leliukh Aleksandr
 */
class CarApplication : Application() {
    val carComponent: CarComponent = DaggerCarComponent.create()
}