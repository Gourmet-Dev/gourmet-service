package com.gourmet.service.place.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class PlaceRouterConfig(
    private val properties: PlaceApiProperties,
    private val handler: PlaceHandler
) {
    @Bean
    fun router() = router {
        properties.root.nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("", handler::getAllPlaces)
            }
        }
    }
}
