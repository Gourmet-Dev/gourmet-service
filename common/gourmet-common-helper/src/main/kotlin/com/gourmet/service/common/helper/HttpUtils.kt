package com.gourmet.service.common.helper

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

object HttpUtils {
    inline fun <reified T : Any> buildResponse(
        flux: Flux<T>,
        type: MediaType = MediaType.APPLICATION_JSON
    ): Mono<ServerResponse> =
        ServerResponse.ok().contentType(type).body(flux, T::class.java)

    inline fun <reified T : Any> buildResponse(
        mono: Mono<T>,
        type: MediaType = MediaType.APPLICATION_JSON
    ): Mono<ServerResponse> =
        ServerResponse.ok().contentType(type).body(mono, T::class.java)

    inline fun <reified T : Any> handleRequestAsMono(request: ServerRequest): Mono<T> =
        request.bodyToMono(T::class.java)

    inline fun <reified T : Any> handleRequestAsFlux(request: ServerRequest): Flux<T> =
        request.bodyToFlux(T::class.java)
}
