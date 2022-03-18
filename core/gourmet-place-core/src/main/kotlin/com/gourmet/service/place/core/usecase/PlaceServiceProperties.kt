package com.gourmet.service.place.core.usecase

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gourmet.place.service")
data class PlaceServiceProperties(
    val repository: String,
)
