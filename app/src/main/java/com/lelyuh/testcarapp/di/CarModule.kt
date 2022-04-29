package com.lelyuh.testcarapp.di

import com.lelyuh.testcarapp.data.api.CarApi
import com.lelyuh.testcarapp.data.repository.CarRepositoryImpl
import com.lelyuh.testcarapp.domain.interactor.CarInteractor
import com.lelyuh.testcarapp.domain.interactor.CarInteractorImpl
import com.lelyuh.testcarapp.domain.rx.RxSchedulers
import com.lelyuh.testcarapp.domain.rx.RxSchedulersImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CarModule {

    @Singleton
    @Provides
    fun provideCarInteractor(): CarInteractor {
        val carApi = Retrofit
            .Builder()
            .baseUrl(CARS_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarApi::class.java)
        return CarInteractorImpl(CarRepositoryImpl(carApi))
    }

    @Singleton
    @Provides
    fun provideRx(): RxSchedulers = RxSchedulersImpl()

    private companion object {
        private const val CARS_BASE_URL = "https://api-aws-eu-qa-1.auto1-test.com/v1/car-types/"
    }
}
