package com.gourmet.service.common.type

abstract class Geospatial<T : Geospatial<T>>(
    val dimension: Int = 0,
    val coordinates: Array<Double>
) {
    abstract fun calcDistance(other: Geospatial<T>) : T
    abstract operator fun plus(other: Geospatial<T>) : T
    abstract operator fun minus(other: Geospatial<T>) : T
    abstract operator fun times(other: Geospatial<T>) : T
    abstract operator fun div(other: Geospatial<T>) : T
    abstract operator fun rem(other: Geospatial<T>) : T
}