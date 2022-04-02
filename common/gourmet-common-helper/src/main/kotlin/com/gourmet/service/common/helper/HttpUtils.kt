package com.gourmet.service.common.helper

import org.slf4j.LoggerFactory

import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicLong

object HttpUtils {
    private val logger = LoggerFactory.getLogger(HttpUtils::class.java)
    private val transactionId = AtomicLong(0)
    private val dataBufferFactory = DefaultDataBufferFactory()

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

    fun identifyTransaction(): Long = transactionId.getAndIncrement()

    fun logRequest(request: ServerRequest, transaction: Long): ServerRequest {
        logger.info(
            "HTTP-Request-{} (path={}, param={}, method={}, type={}, length={})",
            transaction, request.path(), request.queryParams(), request.methodName(),
            request.headers().contentType().orElse(MediaType.ALL),
            request.headers().contentLength().orElse(0),
        )
        return request
    }

    fun logResponse(response: Mono<ServerResponse>, transaction: Long): Mono<ServerResponse> {
        response.subscribe { respRef ->
            logger.info(
                "HTTP-Response-{} (status={})",
                transaction, respRef.statusCode()
            )
        }
        return response
    }
}
