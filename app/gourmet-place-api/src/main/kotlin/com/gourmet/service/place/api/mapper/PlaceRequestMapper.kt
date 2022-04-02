package com.gourmet.service.place.api.mapper

import com.gourmet.service.place.api.payload.GetAllPlacesPayload
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption

object PlaceRequestMapper {
    fun toGetAllPlacesOption(request: GetAllPlacesPayload.Request): GetAllPlacesOption =
        with(request) {
            GetAllPlacesOption(
                location = location?.toGeospatialPoint(),
                paging = paging
            )
        }
}
