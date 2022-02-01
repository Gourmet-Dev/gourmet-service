package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.OffsetDateTime

@Repository("MockedPlaceRepository")
class MockedPlaceRepository : PlaceRepository {
    override fun getAllPlaces(): Flux<Place> {
        val currentTime = OffsetDateTime.now()
        val places = Flux.just(
            MockedPlaceEntity(
                "0",
                "TestPlace0",
                "TestAddress0",
                null,
                emptyList(),
                MockedPlaceEntity.MockedPoint2D(0, 0),
                emptyList(),
                currentTime,
                currentTime
            ),
            MockedPlaceEntity(
                "1",
                "TestPlace1",
                "TestAddress1",
                "TestThumbnail1",
                listOf("TestImage1_0", "TestImage1_1"),
                MockedPlaceEntity.MockedPoint2D(0, 0),
                listOf("TestTag1_0", "TestTag1_1"),
                currentTime.minusDays(5),
                currentTime.minusDays(5)
            )
        )
        return places
            .map { it.toPlace() }
    }
}
