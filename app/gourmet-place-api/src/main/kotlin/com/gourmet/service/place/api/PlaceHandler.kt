package com.gourmet.service.place.api

import com.gourmet.service.common.helper.HttpUtils
import com.gourmet.service.place.api.mapper.PlaceRequestMapper
import com.gourmet.service.place.api.mapper.PlaceResponseMapper
import com.gourmet.service.place.api.payload.GetAllPlacesPayload
import com.gourmet.service.place.core.usecase.PlaceService
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
@ComponentScan("com.gourmet.service")
class PlaceHandler(private val service: PlaceService) {
    fun getAllPlaces(request: ServerRequest): Mono<ServerResponse> {
        return HttpUtils.handleRequestAsMono<GetAllPlacesPayload.Request>(request)
            .switchIfEmpty(Mono.just(GetAllPlacesPayload.Request()))
            .map { PlaceRequestMapper.toGetAllPlacesOption(it) }
            .flatMapMany { service.getAllPlaces(it) }
            .map { PlaceResponseMapper.toGetAllPlacesResponse(it) }
            .let(HttpUtils::buildResponse)
    }
}
