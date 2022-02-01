package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.place.core.domain.Place
import java.time.OffsetDateTime

data class MockedPlaceEntity(
    val id: String? = null,
    val name: String,
    val addr: String,
    val thumbnail: String?,
    val images: List<String>,
    val location: MockedPoint2D,
    val tags: List<String>,
    val createdTime: OffsetDateTime? = OffsetDateTime.now(),
    val updatedTime: OffsetDateTime? = OffsetDateTime.now()
) {
    data class MockedPoint2D(
        val lat: Long,
        val lon: Long
    ) {
        fun toGeospatialPoint() = GeospatialPoint(lat, lon)

        companion object {
            fun fromGeospatialPoint(gpoint: GeospatialPoint) = MockedPoint2D(
                gpoint.coordinates.getOrElse(0) { 0 },
                gpoint.coordinates.getOrElse(1) { 0 }
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
        fun fromPlace(place: Place) = MockedPlaceEntity(
            id = place.id,
            name = place.name,
            addr = place.addr,
            thumbnail = place.thumbnail,
            images = place.images,
            location = MockedPoint2D.fromGeospatialPoint(place.location),
            tags = place.tags,
            createdTime = place.createdTime,
            updatedTime = place.updatedTime
        )
    }
}