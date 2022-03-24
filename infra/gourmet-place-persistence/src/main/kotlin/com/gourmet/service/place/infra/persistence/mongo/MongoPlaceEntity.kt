package com.gourmet.service.place.infra.persistence.mongo

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.place.core.domain.Place
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.OffsetDateTime

@Document(collection = "place")
data class MongoPlaceEntity(
    @Id private val id: ObjectId? = null,
    private val name: String,
    private val addr: String,
    private val thumbnail: String?,
    private val images: List<String>,
    private val location: MongoLocation,
    private val tags: List<String>,
    private val createdTime: OffsetDateTime? = OffsetDateTime.now(),
    private val updatedTime: OffsetDateTime? = OffsetDateTime.now()
) {
    data class MongoLocation(
        val type: Type = Type.Point,
        val coordinates: List<Double> = listOf(0.0, 0.0)
    ) {
        enum class Type(val value: String) {
            Point("Point")
        }

        fun toGeospatialPoint() = GeospatialPoint(coordinates[0], coordinates[1])

        companion object {
            fun fromGeospatialPoint(gpoint: GeospatialPoint) =
                MongoLocation(coordinates = listOf(gpoint.getLatitude(), gpoint.getLongitude()))
        }
    }

    fun toPlace() = Place(
        id = id?.toHexString(),
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
                MongoPlaceEntity(
                    id = if (id != null) ObjectId(id) else null,
                    name = name,
                    addr = addr,
                    thumbnail = thumbnail,
                    images = images,
                    location = MongoLocation.fromGeospatialPoint(location),
                    tags = tags,
                    createdTime = createdTime,
                    updatedTime = updatedTime
                )
            }
    }
}
