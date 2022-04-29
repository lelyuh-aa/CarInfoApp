package com.lelyuh.testcarapp.domain.repository

import io.reactivex.rxjava3.core.Single

/**
 * Repository interface for car information
 *
 * @author Leliukh Aleksandr
 */
interface CarRepository {

    /**
     * Get information about car manufacturers as map of <manufacturerId, manufacturerName>
     *
     * @return [Single] reactive source of [Map]<[String], [String]>
     */
    fun manufacturers(): Single<Map<String, String>>

    /**
     * Get information about models of given manufacturer by it [manufacturerId]
     *
     * @param manufacturerId identifier of given manufacturer
     *
     * @return [Single] reactive source of [List] of [String]
     */
    fun carTypes(manufacturerId: String): Single<List<String>>

    /**
     * Get information about all build dates of given car model by [manufacturerId] and [carModel]
     *
     * @param manufacturerId identifier of given manufacturer
     * @param carModel       name of given car model
     *
     * @return [Single] reactive source of [List] of [String]
     */
    fun carModelBuildDates(manufacturerId: String, carModel: String): Single<List<String>>
}
