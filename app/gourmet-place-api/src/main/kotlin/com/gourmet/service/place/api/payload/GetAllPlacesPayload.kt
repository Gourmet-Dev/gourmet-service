package com.gourmet.service.place.api.payload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.gourmet.service.common.type.PagingInformation
import com.gourmet.service.place.api.serialize.PlaceLocation
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption

object GetAllPlacesPayload {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Response private constructor(
        val id: String,
        val name: String,
        val thumbnail: String?,
        val location: PlaceLocation,
        val tags: List<String>,
    ) {
        companion object {
            fun fromPlace(place: Place) =
                with(place) {
                    Response(
                        id = id!!,
                        name = name,
                        thumbnail = thumbnail,
                        location = PlaceLocation.fromGeospatialPoint(location),
                        tags = tags
                    )
                }
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Request internal constructor(
        val location: PlaceLocation?,
        val paging: PagingInformation?
    ) {
        fun toOption() = GetAllPlacesOption(
            location = location?.toGeospatialPoint(),
            paging = paging
        )
    }
}
