package com.gourmet.service.place.infra.persistence.mongo

import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.usecase.PlaceRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository("MongoPlaceRepository")
class MongoPlaceRepository(private val config: MongoPlaceConfig) : PlaceRepository {
    private val database = config.createDatabaseTemplate()
    override fun getAllPlaces(): Flux<Place> =
        database.findAll(MongoPlaceEntity::class.java).map { it.toPlace() }
}
