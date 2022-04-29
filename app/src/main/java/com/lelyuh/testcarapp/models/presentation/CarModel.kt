package com.lelyuh.testcarapp.models.presentation

import androidx.annotation.DrawableRes
import java.io.Serializable

/**
 * Presentation layer model for represent car item
 *
 * @constructor
 * @property    manufacturerId              manufacturer identifier for requesting additional information if needed
 * @property    manufacturerIconResId       local calculated manufacturer icon
 * @property    manufacturerName            brand name of manufacturer
 * @property    carModelType                brand model of given manufacturer
 * @property    carBuildDate                year of build of given model
 *
 * @author Leliukh Aleksandr
 */
data class CarModel(
    val manufacturerId: String,
    @DrawableRes val manufacturerIconResId: Int,
    val manufacturerName: String,
    var carModelType: String = "",
    var carBuildDate: String = ""
) : Serializable
