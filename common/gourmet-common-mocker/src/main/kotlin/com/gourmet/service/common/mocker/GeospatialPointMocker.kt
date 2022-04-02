package com.gourmet.service.common.mocker

import com.gourmet.service.common.type.GeospatialPoint
import kotlin.random.Random

object GeospatialPointMocker {
    private fun validateCoordinate(coordinate: Double? = null) =
        ((coordinate ?: Random.nextDouble(-180.0, 180.0)) % 180)

    fun create(latitude: Double? = null, longitude: Double? = null) =
        GeospatialPoint(
            validateCoordinate(latitude),
            validateCoordinate(longitude)
        )

    fun create(latitude: Double? = null, longitude: Double? = null, altitude: Double) =
        GeospatialPoint(
            validateCoordinate(latitude),
            validateCoordinate(longitude),
            altitude
        )
}
