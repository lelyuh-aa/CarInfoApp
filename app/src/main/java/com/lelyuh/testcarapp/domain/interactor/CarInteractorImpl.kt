package com.lelyuh.testcarapp.domain.interactor

import com.lelyuh.testcarapp.domain.icon.CarIconHelper.carIcon
import com.lelyuh.testcarapp.domain.repository.CarRepository
import com.lelyuh.testcarapp.models.presentation.CarModel
import io.reactivex.rxjava3.core.Single

/**
 * Interactor implementation for car information
 * Contains all use cases to interact with user actions
 *
 * @constructor
 * @param   repository  repository for loading information about cars
 *
 * @author Leliukh Aleksandr
 */
internal class CarInteractorImpl(
    private val repository: CarRepository
) : CarInteractor {

    override fun manufacturers(): Single<List<CarModel>> =
        repository
            .manufacturers()
            .map { manufacturersMap ->
                manufacturersMap.mapNotNull { entry ->
                    CarModel(
                        entry.key,
                        carIcon(entry.value),
                        entry.value
                    )
                }
            }

    override fun carTypes(manufacturerId: String) = repository.carTypes(manufacturerId)

    override fun carModelBuildDates(manufacturerId: String, carModel: String) =
        repository.carModelBuildDates(manufacturerId, carModel)
}
