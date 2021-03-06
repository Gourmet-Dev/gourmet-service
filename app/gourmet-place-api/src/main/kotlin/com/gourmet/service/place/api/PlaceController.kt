package com.gourmet.service.place.api

import com.gourmet.service.place.api.payload.GetAllPlacesResponse
import com.gourmet.service.place.core.usecase.PlaceService
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/place")
@ComponentScan("com.gourmet.service")
class PlaceController(private val service: PlaceService) {
    @GetMapping("")
    fun getAllPlaces(): Flux<GetAllPlacesResponse> {
        return service.getAllPlaces()
            .map { GetAllPlacesResponse.fromData(it) }
    }
}
