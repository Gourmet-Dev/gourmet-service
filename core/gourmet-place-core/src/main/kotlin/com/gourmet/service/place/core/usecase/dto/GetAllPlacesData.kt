package com.gourmet.service.place.core.usecase.dto

import com.gourmet.service.place.core.domain.Place

data class GetAllPlacesData private constructor(
    val id: Long,
    val name: String,
    val thumbnail: String? = null,
) {
    companion object {
        fun fromPlace(place: Place) = GetAllPlacesData(
            id = place.id!!,
            name = place.name,
            thumbnail = place.thumbnail
        )
    }
}
