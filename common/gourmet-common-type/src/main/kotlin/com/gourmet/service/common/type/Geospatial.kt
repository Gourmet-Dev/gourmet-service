package com.gourmet.service.common.type

abstract class Geospatial(
    val dimension: Int = 0,
    val coordinates: Array<Double>
) {
    companion object {
        const val earthRadiusMeridianDistance: Double = 6367.45
        const val earthRadiusEquatorDistance: Double = 6378.14
        const val earthRadiusAverageDistance: Double = 6372.8
    }
}
