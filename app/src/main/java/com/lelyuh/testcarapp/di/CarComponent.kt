package com.lelyuh.testcarapp.di

import com.lelyuh.testcarapp.presentation.activity.CarBuildDatesListActivity
import com.lelyuh.testcarapp.presentation.activity.CarModelsListActivity
import com.lelyuh.testcarapp.presentation.activity.CarSummaryActivity
import com.lelyuh.testcarapp.presentation.activity.ManufacturerListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CarModule::class])
interface CarComponent {
    fun inject(activity: ManufacturerListActivity)
    fun inject(activity: CarModelsListActivity)
    fun inject(activity: CarBuildDatesListActivity)
    fun inject(activity: CarSummaryActivity)
}