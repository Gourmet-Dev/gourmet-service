package com.gourmet.service.place.core.usecase

import com.gourmet.service.place.core.domain.Place
import reactor.core.publisher.Flux

interface PlaceRepository {
    fun getAllPlaces(): Flux<Place>
}