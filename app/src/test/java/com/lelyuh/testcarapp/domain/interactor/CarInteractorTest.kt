package com.lelyuh.testcarapp.domain.interactor

import com.lelyuh.testcarapp.domain.icon.CarIconHelper
import com.lelyuh.testcarapp.domain.repository.CarRepository
import com.lelyuh.testcarapp.models.presentation.CarModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.net.ConnectException

class CarInteractorTest {

    private val carRepository = mockk<CarRepository>()

    private val carInteractor = CarInteractorImpl(carRepository)

    @Test
    fun `test success get manufacturer list`() {
        mockkObject(CarIconHelper)
        val manufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        every { carRepository.manufacturers() } returns Single.just(manufacturerMap)
        every { CarIconHelper.carIcon("Audi") } returns 123
        every { CarIconHelper.carIcon("BMW") } returns 456

        val expectedResult = listOf(
            CarModel("1", 123, "Audi"),
            CarModel("2", 456, "BMW")
        )

        carInteractor.manufacturers()
            .test()
            .assertValue(expectedResult)

        verify { carRepository.manufacturers() }
        unmockkObject(CarIconHelper)
    }

    @Test
    fun `test error get manufacturer list by server error`() {
        every { carRepository.manufacturers() } returns Single.error(ConnectException())

        carInteractor.manufacturers()
            .test()
            .assertError(ConnectException::class.java)

        verify { carRepository.manufacturers() }
    }

    @Test
    fun `test success get car types`() {
        val manufacturerId = "123"
        val carTypes = listOf("X1", "X3")
        every { carRepository.carTypes(any()) } returns Single.just(carTypes)

        carInteractor.carTypes(manufacturerId)
            .test()
            .assertValue(carTypes)

        verify { carRepository.carTypes(manufacturerId) }
    }

    @Test
    fun `test error get carTypes by server error`() {
        every { carRepository.carTypes(any()) } returns Single.error(ConnectException())

        carInteractor.carTypes("anyId")
            .test()
            .assertError(ConnectException::class.java)

        verify { carRepository.carTypes("anyId") }
    }

    @Test
    fun `test success get car model build dates`() {
        val manufacturerId = "123"
        val carModel = "X3"
        val carModelBuildDates = listOf("2021", "2022")
        every { carRepository.carModelBuildDates(manufacturerId, carModel) } returns Single.just(carModelBuildDates)

        carInteractor.carModelBuildDates(manufacturerId, carModel)
            .test()
            .assertValue(carModelBuildDates)

        verify { carRepository.carModelBuildDates(manufacturerId, carModel) }
    }

    @Test
    fun `test error get car model build dates by server error`() {
        every { carRepository.carModelBuildDates(any(), any()) } returns Single.error(ConnectException())

        carInteractor.carModelBuildDates("anyId", "anyModel")
            .test()
            .assertError(ConnectException::class.java)

        verify { carRepository.carModelBuildDates("anyId", "anyModel") }
    }
}
