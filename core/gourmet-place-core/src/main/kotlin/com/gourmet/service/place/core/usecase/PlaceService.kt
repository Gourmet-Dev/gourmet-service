package com.gourmet.service.place.core.usecase

import com.gourmet.service.place.core.usecase.dto.GetAllPlacesData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
@ComponentScan("com.gourmet.service")
class PlaceService(@Qualifier("placeRepositoryReference") private val repository: PlaceRepository) {
    fun getAllPlaces(): Flux<GetAllPlacesData> {
        return repository.getAllPlaces()
            .map { GetAllPlacesData.fromPlace(it) }
    }
}
