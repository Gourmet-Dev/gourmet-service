package com.gourmet.service.place.core.usecase

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(PlaceServiceProperties::class)
class PlaceServiceConfig(
    private val serviceProperties: PlaceServiceProperties,
    private val appCtx: ApplicationContext
) {
    @Bean
    fun placeRepositoryReference() = appCtx.getBean(serviceProperties.repository) as PlaceRepository
}
