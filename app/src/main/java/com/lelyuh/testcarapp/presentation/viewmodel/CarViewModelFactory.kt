package com.lelyuh.testcarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lelyuh.testcarapp.domain.interactor.CarInteractor
import com.lelyuh.testcarapp.domain.rx.RxSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CarViewModelFactory @Inject constructor(
    private val interactor: CarInteractor,
    private val rxSchedulers: RxSchedulers
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CarViewModel(interactor, rxSchedulers) as T
}
