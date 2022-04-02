package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.common.mocker.GeospatialPointMocker
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.OffsetDateTime
import java.util.Comparator

@Repository("MockedPlaceRepository")
class MockedPlaceRepository : PlaceRepository {
    private val mockedDatabase: Flux<Place> =
        Flux.just(*(Array((0..255).random()) { createPlace() }))

    private fun getAllPlacesWithPagination(page: Long, size: Long): Flux<Place> = mockedDatabase
        .sort(Comparator.comparing(Place::createdTime))
        .skip(size * page)
        .take(size)

    override fun getAllPlaces(option: GetAllPlacesOption): Flux<Place> =
        if (option.pagingInformation != null)
            with(option.pagingInformation!!) {
                getAllPlacesWithPagination(
                    page = page,
                    size = size
                )
            }
        else mockedDatabase

    companion object {
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
    }
}
