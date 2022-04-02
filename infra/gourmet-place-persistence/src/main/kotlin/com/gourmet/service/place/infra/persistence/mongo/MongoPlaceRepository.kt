package com.gourmet.service.place.infra.persistence.mongo

import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository("MongoPlaceRepository")
class MongoPlaceRepository(private val config: MongoPlaceConfig) : PlaceRepository {
    private val database = config.createDatabaseTemplate()

    private fun getAllPlacesWithPagination(page: Long, size: Long): Flux<Place> {
        val query = Query()
            .with(PageRequest.of(page.toInt(), size.toInt()))
        return database.find(query, MongoPlaceEntity::class.java).map { it.toPlace() }
    }

    override fun getAllPlaces(option: GetAllPlacesOption): Flux<Place> =
        if (option.pagingInformation != null)
            with(option.pagingInformation!!) {
                getAllPlacesWithPagination(
                    page = page,
                    size = size
                )
            }
        else
            database.findAll(MongoPlaceEntity::class.java).map { it.toPlace() }
}
