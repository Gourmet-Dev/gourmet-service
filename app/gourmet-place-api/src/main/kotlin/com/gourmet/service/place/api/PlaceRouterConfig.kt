package com.gourmet.service.place.api

import com.gourmet.service.common.helper.HttpUtils
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

        filter { request, handler ->
            val transactionId = HttpUtils.identifyTransaction()
            HttpUtils.logResponse(handler(HttpUtils.logRequest(request, transactionId)), transactionId)
        }
    }
}
