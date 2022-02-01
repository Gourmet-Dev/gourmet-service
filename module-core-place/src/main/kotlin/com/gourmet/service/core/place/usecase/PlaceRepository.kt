package com.gourmet.service.core.place.usecase

import com.gourmet.service.core.place.domain.Place
import reactor.core.publisher.Flux

interface PlaceRepository {
    fun getAllPlaces(): Flux<Place>
}