package com.gourmet.service.place.api.serialize

import com.gourmet.service.common.type.GeospatialPoint

data class PlaceLocation(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun fromGeospatialPoint(geoPoint: GeospatialPoint) =
            PlaceLocation(geoPoint.getLatitude(), geoPoint.getLongitude())
    }
}
