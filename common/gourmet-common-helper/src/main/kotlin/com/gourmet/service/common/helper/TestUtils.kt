package com.gourmet.service.common.helper

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import reactor.core.publisher.Flux

object TestUtils {
    private val jsonMapper = jacksonObjectMapper()

    init {
        jsonMapper.propertyNamingStrategy = PropertyNamingStrategies.SnakeCaseStrategy()
        jsonMapper.registerModule(JavaTimeModule())
    }

    fun <T> listToFlux(list: List<T>): Flux<T> {
        return if (list.isNullOrEmpty()) Flux.empty<T>() else
            Flux.fromIterable(list.asIterable())
    }

    fun <T> listToJson(list: List<T>): String = jsonMapper.writeValueAsString(list)
}
