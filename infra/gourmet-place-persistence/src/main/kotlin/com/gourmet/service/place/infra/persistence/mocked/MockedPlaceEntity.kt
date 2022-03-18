package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.place.core.domain.Place
import java.time.OffsetDateTime

data class MockedPlaceEntity(
    internal val id: Long? = null,
    internal val name: String,
    internal val addr: String,
    internal val thumbnail: String?,
    internal val images: List<String>,
    internal val location: MockedPoint2D,
    internal val tags: List<String>,
    internal val createdTime: OffsetDateTime? = OffsetDateTime.now(),
    internal val updatedTime: OffsetDateTime? = OffsetDateTime.now()
) {
    data class MockedPoint2D(
        val lat: Double,
        val lon: Double
    ) {
        fun toGeospatialPoint() = GeospatialPoint(lat, lon)

        companion object {
            fun fromGeospatialPoint(gpoint: GeospatialPoint) = MockedPoint2D(
                gpoint.getLatitude(),
                gpoint.getLongitude()
            )
        }
    }

    fun toPlace() = Place(
        id = id,
        name = name,
        addr = addr,
        thumbnail = thumbnail,
        images = images,
        location = location.toGeospatialPoint(),
        tags = tags,
        createdTime = createdTime,
        updatedTime = updatedTime
    )

    companion object {
        fun fromPlace(place: Place) =
            with(place) {
                MockedPlaceEntity(
                    id = id,
                    name = name,
                    addr = addr,
                    thumbnail = thumbnail,
                    images = images,
                    location = MockedPoint2D.fromGeospatialPoint(location),
                    tags = tags,
                    createdTime = createdTime,
                    updatedTime = updatedTime
                )
            }
    }
}
