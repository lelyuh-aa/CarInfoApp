package com.lelyuh.testcarapp.models.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Server entity containing information about:
 * - different car manufacturers
 * - all car models for current manufacturer
 * - build years for current model for current manufacturer
 *
 * @constructor
 * @property page           current loaded page, for several queries optional
 * @property pageSize       size of each page, for several queries optional
 * @property totalPageCount common number of pages with information, for several queries optional
 * @property dataMap        information about all manufacturers/models/build years
 *                          representing as Map of data <code, description>
 *
 * @author Leliukh Aleksandr
 */
@Keep
data class CarServerResponse(
    val page: Int?,
    val pageSize: Int?,
    val totalPageCount: Int?,
    @SerializedName("wkda") val dataMap: Map<String, String>
)
