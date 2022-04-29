package com.lelyuh.testcarapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelyuh.testcarapp.domain.interactor.CarInteractor
import com.lelyuh.testcarapp.domain.rx.RxSchedulers
import com.lelyuh.testcarapp.models.presentation.CarModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * View-model for all screens with loading data
 *
 * @constructor
 * @param   interactor      interactor for getting information to represent it to user
 * @param   rxSchedulers    schedulers for async work
 *
 * @author Leliukh Aleksandr
 */
internal class CarViewModel(
    private val interactor: CarInteractor,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val _manufacturerLiveData = MutableLiveData<List<CarModel>>()
    private val _carTypesLiveData = MutableLiveData<List<String>>()
    private val _carBuildDatesLiveData = MutableLiveData<List<String>>()

    private val _errorLiveData = MutableLiveData<Unit>()

    val manufacturerLiveData: LiveData<List<CarModel>>
        get() = _manufacturerLiveData
    val carTypesLiveData: LiveData<List<String>>
        get() = _carTypesLiveData
    val carBuildDatesLiveData: LiveData<List<String>>
        get() = _carBuildDatesLiveData
    val errorLiveData: LiveData<Unit>
        get() = _errorLiveData

    private val disposable = CompositeDisposable()

    fun loadManufacturers() {
        disposable.clear()
        disposable.add(
            interactor
                .manufacturers()
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.ui())
                .subscribe(_manufacturerLiveData::postValue) { throwable ->
                    Log.e("CarViewModel", throwable.message.orEmpty(), throwable)
                    _errorLiveData.postValue(Unit)
                }
        )
    }

    fun loadCarTypes(manufacturerId: String) {
        disposable.clear()
        disposable.add(
            interactor
                .carTypes(manufacturerId)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.ui())
                .subscribe(_carTypesLiveData::postValue) { throwable ->
                    Log.e("CarViewModel", throwable.message.orEmpty(), throwable)
                    _errorLiveData.postValue(Unit)
                }
        )
    }

    fun loadCarModelBuildDates(manufacturerId: String, carTypeModel: String) {
        disposable.clear()
        disposable.add(
            interactor
                .carModelBuildDates(manufacturerId, carTypeModel)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.ui())
                .subscribe(_carBuildDatesLiveData::postValue) { throwable ->
                    Log.e("CarViewModel", throwable.message.orEmpty(), throwable)
                    _errorLiveData.postValue(Unit)
                }
        )
    }
}
