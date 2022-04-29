package com.lelyuh.testcarapp.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lelyuh.testcarapp.domain.interactor.CarInteractor
import com.lelyuh.testcarapp.domain.rx.RxSchedulersStub
import com.lelyuh.testcarapp.models.presentation.CarModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test

class CarViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val carInteractor = mockk<CarInteractor>()
    private val manufacturersObserver = mockk<Observer<List<CarModel>>>(relaxUnitFun = true)
    private val carTypesObserver = mockk<Observer<List<String>>>(relaxUnitFun = true)
    private val carBuildDatesObserver = mockk<Observer<List<String>>>(relaxUnitFun = true)
    private val errorObserver = mockk<Observer<Unit>>(relaxUnitFun = true)

    private val viewModel = CarViewModel(carInteractor, RxSchedulersStub())

    @Test
    fun `test success get manufacturers`() {
        val carModels = listOf(
            CarModel("1", 123, "Audi"),
            CarModel("2", 456, "BMW")
        )
        every { carInteractor.manufacturers() } returns Single.just(carModels)
        viewModel.manufacturerLiveData.observeForever(manufacturersObserver)

        viewModel.loadManufacturers()

        verify { manufacturersObserver.onChanged(carModels) }
    }

    @Test
    fun `test error get manufacturers`() {
        mockkStatic(Log::class)
        every { carInteractor.manufacturers() } returns Single.error(Throwable())
        every { Log.e(any(), any(), any()) } returns 0
        viewModel.errorLiveData.observeForever(errorObserver)

        viewModel.loadManufacturers()

        verify { errorObserver.onChanged(Unit) }
        unmockkStatic(Log::class)
    }

    @Test
    fun `test success car types`() {
        val manufacturerId = "123"
        val carTypes = listOf("X1", "X3")
        every { carInteractor.carTypes(manufacturerId) } returns Single.just(carTypes)
        viewModel.carTypesLiveData.observeForever(carTypesObserver)

        viewModel.loadCarTypes(manufacturerId)

        verify { carTypesObserver.onChanged(carTypes) }
    }

    @Test
    fun `test error car types`() {
        mockkStatic(Log::class)
        val manufacturerId = "123"
        every { carInteractor.carTypes(manufacturerId) } returns Single.error(Throwable())
        every { Log.e(any(), any(), any()) } returns 0
        viewModel.errorLiveData.observeForever(errorObserver)

        viewModel.loadCarTypes(manufacturerId)

        verify { errorObserver.onChanged(Unit) }
        unmockkStatic(Log::class)
    }

    @Test
    fun `test success car model build dates`() {
        val manufacturerId = "123"
        val carModel = "X3"
        val carModelBuildDates = listOf("2021", "2022")
        every { carInteractor.carModelBuildDates(manufacturerId, carModel) } returns Single.just(carModelBuildDates)
        viewModel.carBuildDatesLiveData.observeForever(carBuildDatesObserver)

        viewModel.loadCarModelBuildDates(manufacturerId, carModel)

        verify { carBuildDatesObserver.onChanged(carModelBuildDates) }
    }

    @Test
    fun `test error car model build dates`() {
        mockkStatic(Log::class)
        val manufacturerId = "123"
        val carModel = "X3"
        every { carInteractor.carModelBuildDates(manufacturerId, carModel) } returns Single.error(Throwable())
        every { Log.e(any(), any(), any()) } returns 0
        viewModel.errorLiveData.observeForever(errorObserver)

        viewModel.loadCarModelBuildDates(manufacturerId, carModel)

        verify { errorObserver.onChanged(Unit) }
        unmockkStatic(Log::class)
    }
}
