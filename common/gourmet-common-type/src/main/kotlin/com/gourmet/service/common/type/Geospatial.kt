package com.gourmet.service.common.type

abstract class Geospatial(
    val dimension: Int = 0,
    val coordinates: DoubleArray
) {
    companion object {
        const val EARTH_RADIUS_MERIDIAN_DISTANCE: Double = 6367.45
        const val EARTH_RADIUS_EQUATOR_DISTANCE: Double = 6378.14
        const val EARTH_RADIUS_AVERAGE_DISTANCE: Double = 6372.8
    }
}
