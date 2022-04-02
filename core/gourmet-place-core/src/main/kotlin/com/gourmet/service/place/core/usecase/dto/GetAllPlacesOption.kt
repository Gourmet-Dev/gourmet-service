package com.gourmet.service.place.core.usecase.dto

import com.gourmet.service.common.type.PagingInformation

data class GetAllPlacesOption(
    val paging: PagingInformation? = null,
)
