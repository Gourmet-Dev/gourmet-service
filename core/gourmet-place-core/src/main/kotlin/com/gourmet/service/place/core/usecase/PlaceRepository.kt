package com.gourmet.service.place.core.usecase

import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import reactor.core.publisher.Flux

interface PlaceRepository {
    fun getAllPlaces(option: GetAllPlacesOption): Flux<Place>
}
