package com.gourmet.service.place.api.payload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.gourmet.service.place.api.type.PlaceLocation

object GetAllPlacesPayload {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Response internal constructor(
        val id: String,
        val name: String,
        val thumbnail: String?,
        val location: PlaceLocation,
        val tags: List<String>,
    )
}
