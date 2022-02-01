package com.gourmet.service.common.type

import kotlin.math.abs

class GeospatialPoint constructor(vararg params: Double) : Geospatial<GeospatialPoint>(params.size, params.toTypedArray()) {
    override fun calcDistance(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val point = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            point.coordinates[index] = abs(pair.first - pair.second)
        }
        return point
    }

    override fun plus(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val point = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            point.coordinates[index] = pair.first + pair.second
        }
        return point
    }

    override fun minus(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val point = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            point.coordinates[index] = pair.first - pair.second
        }
        return point
    }

    override fun times(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val point = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            point.coordinates[index] = pair.first * pair.second
        }
        return point
    }

    override fun div(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val point = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            point.coordinates[index] = pair.first / pair.second
        }
        return point
    }

    override fun rem(other: Geospatial<GeospatialPoint>): GeospatialPoint {
        val geospatialPoint = GeospatialPoint(
            *(if (dimension > other.dimension) coordinates else other.coordinates).toDoubleArray()
        )
        coordinates.zip(other.coordinates).mapIndexed { index, pair ->
            geospatialPoint.coordinates[index] = pair.first % pair.second
        }
        return geospatialPoint
    }
}

