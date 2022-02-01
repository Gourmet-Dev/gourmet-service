package com.gourmet.service.api.place.payload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.gourmet.service.core.place.usecase.dto.GetAllPlacesData

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetAllPlacesResponse private constructor(
    val id: String,
    val name: String,
    val thumbnail: String?
) {
    companion object {
        fun fromData(data: GetAllPlacesData) = GetAllPlacesResponse(
            id = data.id,
            name = data.name,
            thumbnail = data.thumbnail
        )
    }
}