package com.gourmet.service.place.core.domain

import com.gourmet.service.common.type.GeospatialPoint
import java.time.OffsetDateTime

data class Place(
    val id: String?,
    val name: String,
    val addr: String,
    val thumbnail: String?,
    val images: List<String>,
    val location: GeospatialPoint,
    val tags: List<String>,
    val createdTime: OffsetDateTime?,
    val updatedTime: OffsetDateTime?
)
