package com.gourmet.service.common.type

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

class GeospatialPoint constructor(vararg params: Double) : Geospatial(params.size, params.toTypedArray()) {
    enum class DistanceFormula {
        HAVERSINE_DISTANCE_FORMULA {
            override fun calculate(source: GeospatialPoint, destination: GeospatialPoint): Double {
                val haversineFunction: (Double) -> Double = { radian -> ((1 - cos(radian)) / 2) }
                val latDelta = (destination.getLatitude() - source.getLatitude()).toRadian()
                val lonDelta = (destination.getLongitude() - source.getLongitude()).toRadian()
                val latDeltaHav = haversineFunction(latDelta)
                val lonDeltaHav = haversineFunction(lonDelta)
                val sqrtResult = sqrt(
                    latDeltaHav +
                        cos(source.getLatitude().toRadian()) *
                        cos(destination.getLatitude().toRadian()) *
                        lonDeltaHav
                )
                return 2 * EARTH_RADIUS_AVERAGE_DISTANCE * asin(sqrtResult)
            }
        };
        abstract fun calculate(source: GeospatialPoint, destination: GeospatialPoint): Double
    }

    fun getLatitude(): Double = coordinates.getOrElse(0) { 0.0 }
    fun getLongitude(): Double = coordinates.getOrElse(1) { 0.0 }
    fun getAltitude(): Double = (coordinates.getOrElse(2) { 0.0 }) / 1000

    fun calcDistance(
        destination: GeospatialPoint,
        formula: DistanceFormula = DistanceFormula.HAVERSINE_DISTANCE_FORMULA
    ): Double = sqrt(
        formula.calculate(this, destination).pow(2) +
            (getAltitude() - destination.getAltitude()).pow(2)
    )
}
