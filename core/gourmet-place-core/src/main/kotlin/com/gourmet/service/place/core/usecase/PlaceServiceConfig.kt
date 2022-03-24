package com.gourmet.service.place.core.usecase

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlaceServiceConfig(
    private val placeServiceProperties: PlaceServiceProperties,
    private val appCtx: ApplicationContext
) {
    @Bean
    fun placeRepositoryReference() =
        appCtx.getBean(placeServiceProperties.repository) as PlaceRepository
}
