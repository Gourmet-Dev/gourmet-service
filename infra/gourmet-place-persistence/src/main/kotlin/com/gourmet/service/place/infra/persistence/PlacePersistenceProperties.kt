package com.gourmet.service.place.infra.persistence

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gourmet.place.persistence")
data class PlacePersistenceProperties(
    val database: String,
    val host: String,
    val port: String,
    val username: String?,
    val password: String?
)
