package com.gourmet.service.place.core.usecase.dto

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.common.type.PagingInformation

data class GetAllPlacesOption(
    val location: GeospatialPoint? = null,
    val paging: PagingInformation? = null,
)
