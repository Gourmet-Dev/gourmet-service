package com.gourmet.service.place.api.mapper

import com.gourmet.service.place.api.payload.GetAllPlacesPayload
import com.gourmet.service.place.api.type.PlaceLocation
import com.gourmet.service.place.core.domain.Place

object PlaceResponseMapper {
    fun toGetAllPlacesResponse(place: Place): GetAllPlacesPayload.Response =
        with(place) {
            GetAllPlacesPayload.Response(
                id = id!!,
                name = name,
                thumbnail = thumbnail,
                location = PlaceLocation.fromGeospatialPoint(location),
                tags = tags
            )
        }
}
