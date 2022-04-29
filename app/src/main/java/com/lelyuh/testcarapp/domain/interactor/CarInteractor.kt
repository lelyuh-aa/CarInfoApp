package com.lelyuh.testcarapp.domain.interactor

import com.lelyuh.testcarapp.models.presentation.CarModel
import io.reactivex.rxjava3.core.Single

/**
 * Interactor interface for car information
 * Contains all use cases to interact with user actions
 *
 * @author Leliukh Aleksandr
*/
interface CarInteractor {

    /**
     * Get information about car manufacturers as list of [CarModel]
     *
     * @return [Single] reactive source of [Map]<[Int], [String]>
     */
    fun manufacturers(): Single<List<CarModel>>

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