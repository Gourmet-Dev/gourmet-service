package com.gourmet.service.common.type

import kotlin.math.*

class GeospatialPoint constructor(
    vararg params: Double,
    val distanceFormula: (GeospatialPoint, GeospatialPoint) -> Double = HAVERSINE_DISTANCE_FORMULA
) : Geospatial(params.size, params.toTypedArray()) {
    fun getLatitude(): Double = coordinates.getOrElse(0) { 0.0 }
    fun getLongitude(): Double = coordinates.getOrElse(1) { 0.0 }
    fun getAltitude(): Double = (coordinates.getOrElse(2) { 0.0 }) / 1000

    fun calcDistance(other: GeospatialPoint) = sqrt(
        distanceFormula(this, other).pow(2) + abs(getAltitude() - other.getAltitude()).pow(2)
    )

    companion object {
        val HAVERSINE_DISTANCE_FORMULA = fun(source: GeospatialPoint, destination: GeospatialPoint): Double {
            val haversineFunction: (Double) -> Double = { radian -> ((1 - cos(radian)) / 2) }
            val latDelta = abs(destination.getLatitude() - source.getLatitude()).toRadian()
            val lonDelta = abs(destination.getLongitude() - source.getLongitude()).toRadian()
            val latDeltaHav = haversineFunction(latDelta)
            val lonDeltaHav = haversineFunction(lonDelta)
            val sqrtResult = sqrt(
                latDeltaHav + cos(source.getLatitude().toRadian()) * cos(destination.getLatitude().toRadian()) * lonDeltaHav
            )
            return 2 * earthRadiusAverageDistance * asin(sqrtResult)
        }
    }
}
