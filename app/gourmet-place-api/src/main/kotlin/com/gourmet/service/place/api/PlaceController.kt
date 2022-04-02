package com.gourmet.service.place.api

import com.gourmet.service.common.type.PagingInformation
import com.gourmet.service.place.api.payload.GetAllPlacesResponse
import com.gourmet.service.place.core.usecase.PlaceService
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/place")
@ComponentScan("com.gourmet.service")
class PlaceController(private val service: PlaceService) {
    @GetMapping("")
    fun getAllPlaces(
        @RequestParam("page", required = false) page: Long?,
        @RequestParam("size", required = false) size: Long?
    ): Flux<GetAllPlacesResponse> {
        val option = GetAllPlacesOption(
            if (page != null && size != null) PagingInformation(page, size) else null
        )
        return service.getAllPlaces(option)
            .map { GetAllPlacesResponse.fromPlace(it) }
    }
}
