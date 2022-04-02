package com.gourmet.service.common.mocker

import com.gourmet.service.common.type.GeospatialPoint
import kotlin.random.Random

object GeospatialPointMocker {
    private fun validateCoordinate(coordinate: Double? = null) =
        ((coordinate ?: Random.nextDouble(-180.0, 180.0)) % 180)

    fun create(latitude: Double? = null, longitude: Double? = null, altitude: Double? = null) =
        GeospatialPoint(
            validateCoordinate(latitude),
            validateCoordinate(longitude),
            altitude ?: Random.nextDouble(0.0, 1000000.0)
        )
}
