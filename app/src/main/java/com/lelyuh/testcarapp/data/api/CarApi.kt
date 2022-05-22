package com.lelyuh.testcarapp.data.api

import com.lelyuh.testcarapp.models.data.CarServerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for interaction with server via Retrofit library
 *
 * @author Leliukh Aleksandr
 */
interface CarApi {

    /**
     * Get given page of manufacturer list with page size 15 rows
     */
    @GET(MANUFACTURER_PATH)
    fun manufacturers(
        @Query("page") currentPage: Int,
        @Query("pageSize") pageSize: Int = MANUFACTURER_PAGE_SIZE,
        @Query("wa_key") apiKey: String = API_KEY
    ): Single<CarServerResponse>

    /**
     * Get all car models of given manufacturer by [manufacturerId]
     */
    @GET(CAR_TYPES_PATH)
    fun carTypes(
        @Query("manufacturer") manufacturerId: String,
        @Query("wa_key") apiKey: String = API_KEY
    ): Single<CarServerResponse>

    /**
     * Get all build dates of given car model by [manufacturerId] and [carModel]
     */
    @GET(CAR_TYPE_BUILD_DATES_PATH)
    fun carModelBuildDates(
        @Query("manufacturer") manufacturerId: String,
        @Query("main-type") carModel: String,
        @Query("wa_key") apiKey: String = API_KEY
    ): Single<CarServerResponse>

    private companion object {
        private const val API_KEY = "read README.md file"

        private const val MANUFACTURER_PATH = "manufacturer"
        private const val CAR_TYPES_PATH = "main-types"
        private const val CAR_TYPE_BUILD_DATES_PATH = "built-dates"

        private const val MANUFACTURER_PAGE_SIZE = 15
    }
}
