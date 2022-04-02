package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.common.mocker.GeospatialPointMocker
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.OffsetDateTime

@Repository("MockedPlaceRepository")
class MockedPlaceRepository : PlaceRepository {
    private fun createPlace(): Place {
        val id = ObjectId().toHexString()
        return Place(
            id,
            "MockedPlace$id",
            "MockedAddress$id",
            "MockedThumbnail$id",
            listOf("MockedImage$id"),
            GeospatialPointMocker.create(),
            listOf("MockedTag$id"),
            OffsetDateTime.now(),
            OffsetDateTime.now()
        )
    }

    override fun getAllPlaces(): Flux<Place> =
        Flux.just(*(Array((0..255).random()) { createPlace() }))
}
