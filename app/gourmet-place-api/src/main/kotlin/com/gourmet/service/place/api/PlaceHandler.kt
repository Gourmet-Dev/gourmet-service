package com.gourmet.service.place.api

import com.gourmet.service.common.helper.HttpUtils
import com.gourmet.service.common.helper.RequestParamMapper
import com.gourmet.service.place.api.mapper.PlaceResponseMapper
import com.gourmet.service.place.core.usecase.PlaceService
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
@ComponentScan("com.gourmet.service")
class PlaceHandler(private val service: PlaceService) {
    fun getAllPlaces(request: ServerRequest): Mono<ServerResponse> {
        val option = GetAllPlacesOption(
            RequestParamMapper.toGeospatialPoint(request),
            RequestParamMapper.toPagingInformation(request)
        )
        return service.getAllPlaces(option)
            .map { PlaceResponseMapper.toGetAllPlacesResponse(it) }
            .let(HttpUtils::buildResponse)
    }
}
