package com.gourmet.service.place.infra.persistence.mocked

import com.gourmet.service.common.mocker.GeospatialPointMocker
import com.gourmet.service.common.type.PagingInformation
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.OffsetDateTime

@Repository("MockedPlaceRepository")
class MockedPlaceRepository : PlaceRepository {
    private val mockedDatabase: Flux<Place> =
        Flux.just(*(Array((10..255).random()) { createPlace() }))

    private fun getAllPlacesWithPagination(paging: PagingInformation): Flux<Place> =
        paging.paginateFlux(mockedDatabase)

    override fun getAllPlaces(option: GetAllPlacesOption): Flux<Place> =
        if (option.paging != null)
            getAllPlacesWithPagination(option.paging!!)
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
