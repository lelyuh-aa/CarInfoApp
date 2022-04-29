package com.lelyuh.testcarapp.data.repository

import com.lelyuh.testcarapp.data.api.CarApi
import com.lelyuh.testcarapp.models.data.CarServerResponseBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.net.ConnectException

class CarRepositoryTest {

    private val carApi = mockk<CarApi>()

    private val carRepository = CarRepositoryImpl(carApi)

    @Test
    fun `test success get manufacturer list first call`() {
        val manufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 2,
            dataMap = manufacturerMap
        )
        every { carApi.manufacturers(0) } returns Single.just(response)

        carRepository.manufacturers()
            .test()
            .assertValue(manufacturerMap)

        verify { carApi.manufacturers(0) }
    }

    @Test
    fun `test success get manufacturer full list with concat data`() {
        val firstManufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        val secondManufacturerMap = mapOf("3" to "Mercedes", "4" to "Ferrari")
        val expectedFinalData = firstManufacturerMap.plus(secondManufacturerMap)

        val firstResponse = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 2,
            dataMap = firstManufacturerMap
        )
        val secondResponse = CarServerResponseBean(
            page = 1,
            pageSize = 2,
            totalPageCount = 2,
            dataMap = secondManufacturerMap
        )
        every { carApi.manufacturers(0) } returns Single.just(firstResponse)
        every { carApi.manufacturers(1) } returns Single.just(secondResponse)


        carRepository.manufacturers()
            .test()
            .assertValue(firstManufacturerMap)

        carRepository.manufacturers()
            .test()
            .assertValue(expectedFinalData)

        verify {
            carApi.manufacturers(0)
            carApi.manufacturers(1)
        }
    }

    @Test
    fun `test success get manufacturer full list from cache with more than total page calls`() {
        val firstManufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        val secondManufacturerMap = mapOf("3" to "Mercedes", "4" to "Ferrari")
        val expectedFinalData = firstManufacturerMap.plus(secondManufacturerMap)

        val firstResponse = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 2,
            dataMap = firstManufacturerMap
        )
        val secondResponse = CarServerResponseBean(
            page = 1,
            pageSize = 2,
            totalPageCount = 2,
            dataMap = secondManufacturerMap
        )
        every { carApi.manufacturers(0) } returns Single.just(firstResponse)
        every { carApi.manufacturers(1) } returns Single.just(secondResponse)

        carRepository.manufacturers()
            .test()
            .assertValue(firstManufacturerMap)

        carRepository.manufacturers()
            .test()
            .assertValue(expectedFinalData)

        carRepository.manufacturers()
            .test()
            .assertValue(expectedFinalData)

        verify {
            carApi.manufacturers(0)
            carApi.manufacturers(1)
        }
    }

    @Test
    fun `test error get manufacturer by null current page`() {
        val manufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        val response = CarServerResponseBean(
            page = null,
            pageSize = 2,
            totalPageCount = 1,
            dataMap = manufacturerMap
        )
        every { carApi.manufacturers(0) } returns Single.just(response)

        carRepository.manufacturers()
            .test()
            .assertError(IllegalStateException::class.java)

        verify { carApi.manufacturers(0) }
    }

    @Test
    fun `test error get manufacturer by null total pages count`() {
        val manufacturerMap = mapOf("1" to "Audi", "2" to "BMW")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = null,
            dataMap = manufacturerMap
        )
        every { carApi.manufacturers(0) } returns Single.just(response)

        carRepository.manufacturers()
            .test()
            .assertError(IllegalStateException::class.java)

        verify { carApi.manufacturers(0) }
    }

    @Test
    fun `test error get manufacturer by server error`() {
        every { carApi.manufacturers(0) } returns Single.error(ConnectException())

        carRepository.manufacturers()
            .test()
            .assertError(ConnectException::class.java)

        verify { carApi.manufacturers(0) }
    }

    @Test
    fun `test success carTypes from server`() {
        val manufacturerId = "123"
        val carTypesMap = mapOf("Audi" to "Audi", "BMW" to "BMW")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 1,
            dataMap = carTypesMap
        )
        every { carApi.carTypes(manufacturerId) } returns Single.just(response)
        val expectedResult = listOf("Audi", "BMW")

        carRepository.carTypes(manufacturerId)
            .test()
            .assertValue(expectedResult)

        verify { carApi.carTypes(manufacturerId) }
    }

    @Test
    fun `test success carTypes from cache on second call`() {
        val manufacturerId = "123"
        val carTypesMap = mapOf("Audi" to "Audi", "BMW" to "BMW")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 1,
            dataMap = carTypesMap
        )
        every { carApi.carTypes(manufacturerId) } returns Single.just(response)
        val expectedResult = listOf("Audi", "BMW")

        carRepository.carTypes(manufacturerId)
            .test()
            .assertValue(expectedResult)

        carRepository.carTypes(manufacturerId)
            .test()
            .assertValue(expectedResult)

        verify { carApi.carTypes(manufacturerId) }
    }

    @Test
    fun `test error get carTypes by server error`() {
        every { carApi.carTypes(any()) } returns Single.error(ConnectException())

        carRepository.carTypes("any")
            .test()
            .assertError(ConnectException::class.java)

        verify { carApi.carTypes("any") }
    }

    @Test
    fun `test success carModelBuildDates from server`() {
        val manufacturerId = "123"
        val carModel = "BMW"
        val buildDatesMap = mapOf("2021" to "2021", "2022" to "2022")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 1,
            dataMap = buildDatesMap
        )
        every { carApi.carModelBuildDates(manufacturerId, carModel) } returns Single.just(response)
        val expectedResult = listOf("2021", "2022")

        carRepository.carModelBuildDates(manufacturerId, carModel)
            .test()
            .assertValue(expectedResult)

        verify { carApi.carModelBuildDates(manufacturerId, carModel) }
    }

    @Test
    fun `test success carModelBuildDates from cache on second call`() {
        val manufacturerId = "123"
        val carModel = "BMW"
        val buildDatesMap = mapOf("2021" to "2021", "2022" to "2022")
        val response = CarServerResponseBean(
            page = 0,
            pageSize = 2,
            totalPageCount = 1,
            dataMap = buildDatesMap
        )
        every { carApi.carModelBuildDates(manufacturerId, carModel) } returns Single.just(response)
        val expectedResult = listOf("2021", "2022")

        carRepository.carModelBuildDates(manufacturerId, carModel)
            .test()
            .assertValue(expectedResult)

        carRepository.carModelBuildDates(manufacturerId, carModel)
            .test()
            .assertValue(expectedResult)

        verify { carApi.carModelBuildDates(manufacturerId, carModel) }
    }

    @Test
    fun `test error get carModelBuildDates by server error`() {
        every { carApi.carModelBuildDates(any(), any()) } returns Single.error(ConnectException())

        carRepository.carModelBuildDates("anyId", "anyModel")
            .test()
            .assertError(ConnectException::class.java)

        verify { carApi.carModelBuildDates("anyId", "anyModel") }
    }
}
