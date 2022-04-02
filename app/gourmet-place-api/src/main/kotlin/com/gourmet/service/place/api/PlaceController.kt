package com.gourmet.service.place.api

import com.gourmet.service.place.api.payload.GetAllPlacesPayload
import com.gourmet.service.place.core.usecase.PlaceService
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/place")
@ComponentScan("com.gourmet.service")
class PlaceController(private val service: PlaceService) {
    @GetMapping("")
    fun getAllPlaces(
        @RequestBody(required = false) request: GetAllPlacesPayload.Request?
    ): Flux<GetAllPlacesPayload.Response> {
        val option = request?.toOption() ?: GetAllPlacesOption()
        return service.getAllPlaces(option)
            .map { GetAllPlacesPayload.Response.fromPlace(it) }
    }
}
