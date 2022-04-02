package com.gourmet.service.place.core.usecase.dto

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.place.core.domain.Place

data class GetAllPlacesData private constructor(
    val id: String,
    val name: String,
    val thumbnail: String?,
    val location: GeospatialPoint,
    val tags: List<String>,
) {
    companion object {
        fun fromPlace(place: Place) =
            with(place) {
                GetAllPlacesData(
                    id = id!!,
                    name = name,
                    thumbnail = thumbnail,
                    location = location,
                    tags = tags
                )
            }
    }
}
