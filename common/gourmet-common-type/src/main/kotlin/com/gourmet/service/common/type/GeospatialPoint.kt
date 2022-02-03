package com.gourmet.service.common.type

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

class GeospatialPoint constructor(vararg params: Double) : Geospatial(params.size, params.toTypedArray()) {
    fun getLatitude(): Double = coordinates.getOrElse(0) { 0.0 }
    fun getLongitude(): Double = coordinates.getOrElse(1) { 0.0 }
    fun getAltitude(): Double = (coordinates.getOrElse(2) { 0.0 }) / 1000

    companion object {
        val HAVERSINE_DISTANCE_FORMULA = fun(source: GeospatialPoint, destination: GeospatialPoint): Double {
            val haversineFunction: (Double) -> Double = { radian -> ((1 - cos(radian)) / 2) }
            val latDelta = (destination.getLatitude() - source.getLatitude()).toRadian()
            val lonDelta = (destination.getLongitude() - source.getLongitude()).toRadian()
            val latDeltaHav = haversineFunction(latDelta)
            val lonDeltaHav = haversineFunction(lonDelta)
            val sqrtResult = sqrt(
                latDeltaHav +
                    cos(source.getLatitude().toRadian()) * cos(destination.getLatitude().toRadian()) * lonDeltaHav
            )
            return 2 * earthRadiusAverageDistance * asin(sqrtResult)
        }

        fun calcDistance(
            source: GeospatialPoint,
            destination: GeospatialPoint,
            formula: (GeospatialPoint, GeospatialPoint) -> Double = HAVERSINE_DISTANCE_FORMULA
        ) = sqrt(
            formula(source, destination).pow(2) + (source.getAltitude() - destination.getAltitude()).pow(2)
        )
    }
}
