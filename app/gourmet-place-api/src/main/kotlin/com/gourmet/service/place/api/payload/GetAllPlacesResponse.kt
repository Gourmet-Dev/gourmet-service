package com.gourmet.service.place.api.payload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesData

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetAllPlacesResponse private constructor(
    val id: Long,
    val name: String,
    val thumbnail: String?
) {
    companion object {
        fun fromData(data: GetAllPlacesData) =
            with(data) {
                GetAllPlacesResponse(
                    id = id,
                    name = name,
                    thumbnail = thumbnail
                )
            }
    }
}
