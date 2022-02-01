package com.gourmet.service.core.place.usecase

import com.gourmet.service.core.place.usecase.dto.GetAllPlacesData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PlaceService(@Qualifier("MockedPlaceRepository") private val repository: PlaceRepository) {
    fun getAllPlaces(): Flux<GetAllPlacesData> {
        return repository.getAllPlaces()
            .map { GetAllPlacesData.fromPlace(it) }
    }
}