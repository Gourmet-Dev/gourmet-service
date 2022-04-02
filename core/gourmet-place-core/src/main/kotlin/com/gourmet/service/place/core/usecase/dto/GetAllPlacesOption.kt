package com.gourmet.service.place.core.usecase.dto

data class GetAllPlacesOption(
    val pagingInformation: Paging? = null,
) {
    data class Paging(
        val page: Long,
        val size: Long
    )
}
