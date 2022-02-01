package com.gourmet.service.core.place.usecase.dto

import com.gourmet.service.core.place.domain.Place

data class GetAllPlacesData private constructor(
    val id: String,
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