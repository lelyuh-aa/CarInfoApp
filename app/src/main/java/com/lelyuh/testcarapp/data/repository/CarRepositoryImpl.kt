package com.lelyuh.testcarapp.data.repository

import com.lelyuh.testcarapp.data.api.CarApi
import com.lelyuh.testcarapp.domain.repository.CarRepository
import io.reactivex.rxjava3.core.Single

/**
 * Repository implementation for car information
 * Realize caching of loaded information to avoid redundant queries to server
 *
 * @constructor
 * @param   carApi  server api for loading information about cars
 *
 * @author Leliukh Aleksandr
 */
internal class CarRepositoryImpl(
    private val carApi: CarApi
) : CarRepository {

    private val cacheManufacturers = LinkedHashMap<String, String>()
    private var totalPagesManufacturers = 1
    private var currentLoadedPageManufacturers = -1
    private val cacheCarTypes = HashMap<String, Set<String>>()
    private val cacheBuildDates = HashMap<Pair<String, String>, Set<String>>()

    override fun manufacturers(): Single<Map<String, String>> =
        if (currentLoadedPageManufacturers < totalPagesManufacturers - 1) {
            carApi
                .manufacturers(currentLoadedPageManufacturers + 1)
                .map { response ->
                    currentLoadedPageManufacturers =
                        response.page ?: throw IllegalStateException("Current loaded page number is required")
                    totalPagesManufacturers =
                        response.totalPageCount ?: throw IllegalStateException("Total page number is required")
                    cacheManufacturers.putAll(response.dataMap)
                    cacheManufacturers
                }
        } else {
            Single.just(cacheManufacturers)
        }

    override fun carTypes(manufacturerId: String): Single<List<String>> =
        cacheCarTypes[manufacturerId]?.let { cachedTypes ->
            Single.just(cachedTypes.toList())
        } ?: carApi
            .carTypes(manufacturerId)
            .map { response ->
                val carTypes = response.dataMap.keys
                cacheCarTypes[manufacturerId] = carTypes
                carTypes.toList()
            }

    override fun carModelBuildDates(manufacturerId: String, carModel: String): Single<List<String>> {
        val cacheKey = Pair(manufacturerId, carModel)
        return cacheBuildDates[cacheKey]?.let { cachedDates ->
            Single.just(cachedDates.toList())
        } ?: carApi
            .carModelBuildDates(manufacturerId, carModel)
            .map { response ->
                val carBuildDates = response.dataMap.keys
                cacheBuildDates[cacheKey] = carBuildDates
                carBuildDates.toList()
            }
    }
}
