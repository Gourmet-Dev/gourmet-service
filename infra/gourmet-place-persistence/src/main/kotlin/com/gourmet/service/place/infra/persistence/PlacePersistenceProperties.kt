package com.gourmet.service.place.infra.persistence

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class PlacePersistenceProperties(
    @Value("\${gourmet.place.persistence.database}")
    val database: String,
    @Value("\${gourmet.place.persistence.host}")
    val host: String,
    @Value("\${gourmet.place.persistence.port}")
    val port: String,
    @Value("\${gourmet.place.persistence.username:#{null}}")
    val username: String?,
    @Value("\${gourmet.place.persistence.password:#{null}}")
    val password: String?
)
