package com.gourmet.service.place.api.payload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.gourmet.service.place.api.serialize.PlaceLocation
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesData

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetAllPlacesResponse private constructor(
    val id: String,
    val name: String,
    val thumbnail: String?,
    val location: PlaceLocation,
    val tags: List<String>,
) {
    companion object {
        fun fromData(data: GetAllPlacesData) =
            with(data) {
                GetAllPlacesResponse(
                    id = id,
                    name = name,
                    thumbnail = thumbnail,
                    location = PlaceLocation.fromGeospatialPoint(location),
                    tags = tags
                )
            }

        fun fromPlace(place: Place) =
            with(place) {
                GetAllPlacesResponse(
                    id = id!!,
                    name = name,
                    thumbnail = thumbnail,
                    location = PlaceLocation.fromGeospatialPoint(location),
                    tags = tags
                )
            }
    }
}
