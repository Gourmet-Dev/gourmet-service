package com.gourmet.service.infra.place.persistence

import com.gourmet.service.common.type.GeospatialPoint
import com.gourmet.service.core.place.domain.Place
import com.gourmet.service.core.place.usecase.PlaceRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.OffsetDateTime

@Repository("MockedPlaceRepository")
class MockedPlaceRepository : PlaceRepository {
    override fun getAllPlaces(): Flux<Place> {
        val currentTime = OffsetDateTime.now()
        return Flux.just(
            Place(
                "0",
                "TestPlace0",
                "TestAddress0",
                null,
                emptyList(),
                GeospatialPoint(0, 0, 0),
                emptyList(),
                currentTime,
                currentTime
            ),
            Place(
                "1",
                "TestPlace1",
                "TestAddress1",
                "TestThumbnail1",
                listOf("TestImage1_0", "TestImage1_1"),
                GeospatialPoint(0, 0, 0),
                listOf("TestTag1_0", "TestTag1_1"),
                currentTime.minusDays(5),
                currentTime.minusDays(5)
            )
        )
    }
}
