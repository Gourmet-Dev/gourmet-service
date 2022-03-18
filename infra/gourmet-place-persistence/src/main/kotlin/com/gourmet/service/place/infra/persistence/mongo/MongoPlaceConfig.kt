package com.gourmet.service.place.infra.persistence.mongo

import com.gourmet.service.common.helper.OffsetDateTimeUtils
import com.gourmet.service.place.infra.persistence.PlacePersistenceProperties
import com.mongodb.ConnectionString
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackageClasses = [MongoPlaceRepository::class])
class MongoPlaceConfig(private val persistenceProperties: PlacePersistenceProperties) {
    private fun buildConnectionURI(): String {
        val connectionProtocol = "mongodb://"
        val connectionURN =
            "${persistenceProperties.host}:${persistenceProperties.port}/${persistenceProperties.database}"

        return if (persistenceProperties.username != null && persistenceProperties.password != null)
            "$connectionProtocol${persistenceProperties.username}:${persistenceProperties.password}@$connectionURN"
        else
            "$connectionProtocol$connectionURN"
    }

    private fun createDatabaseFactory(): ReactiveMongoDatabaseFactory =
        SimpleReactiveMongoDatabaseFactory(ConnectionString(buildConnectionURI()))

    private fun createMappingConverter(): MongoConverter {
        val dbRefResolver = NoOpDbRefResolver.INSTANCE
        val newConverter = MappingMongoConverter(dbRefResolver, MongoMappingContext())
        val customConversion = MongoCustomConversions(
            listOfNotNull(
                OffsetDateTimeUtils.ReadStringifiedOffsetDateTime(),
                OffsetDateTimeUtils.WriteStringifiedOffsetDateTime()
            )
        )
        newConverter.setCustomConversions(customConversion)
        return newConverter
    }

    fun createDatabaseTemplate(): ReactiveMongoTemplate =
        ReactiveMongoTemplate(createDatabaseFactory(), createMappingConverter())
}
