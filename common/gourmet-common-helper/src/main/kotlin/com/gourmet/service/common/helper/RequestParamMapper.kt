package com.gourmet.service.common.helper

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.common.type.PagingInformation
import org.springframework.web.reactive.function.server.ServerRequest

object RequestParamMapper {
    private val logger = logger()

    fun toGeospatialPoint(request: ServerRequest): GeospatialPoint? {
        val longitudeParam = HttpUtils.parseRequestParam(request, "longitude")?.toDoubleOrNull()
        val latitudeParam = HttpUtils.parseRequestParam(request, "latitude")?.toDoubleOrNull()

        return if (longitudeParam != null && latitudeParam != null)
            GeospatialPoint(longitudeParam, latitudeParam)
        else null
    }
    fun toPagingInformation(request: ServerRequest): PagingInformation? {
        val pageParam = HttpUtils.parseRequestParam(request, "page")?.toLongOrNull()
        val sizeParam = HttpUtils.parseRequestParam(request, "size")?.toLongOrNull()

        return if (pageParam != null && sizeParam != null)
            PagingInformation(pageParam, sizeParam)
        else null
    }
}
