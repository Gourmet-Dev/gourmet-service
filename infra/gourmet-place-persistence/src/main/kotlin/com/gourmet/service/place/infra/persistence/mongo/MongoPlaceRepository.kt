package com.gourmet.service.place.infra.persistence.mongo

import com.gourmet.service.common.type.PagingInformation
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesOption
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository("MongoPlaceRepository")
class MongoPlaceRepository(private val config: MongoPlaceConfig) : PlaceRepository {
    private val database = config.createDatabaseTemplate()

    private fun getAllPlacesWithPagination(paging: PagingInformation): Flux<Place> {
        val query = Query()
            .with(paging.toPageable())
        return database.find(query, MongoPlaceEntity::class.java).map { it.toPlace() }
    }

    override fun getAllPlaces(option: GetAllPlacesOption): Flux<Place> =
        if (option.paging != null)
            getAllPlacesWithPagination(option.paging!!)
        else
            database.findAll(MongoPlaceEntity::class.java).map { it.toPlace() }
}
