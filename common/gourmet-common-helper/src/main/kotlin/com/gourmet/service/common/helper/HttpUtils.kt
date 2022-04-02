package com.gourmet.service.common.helper

import com.gourmet.service.common.type.GourmetException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicLong

object HttpUtils {
    private val logger = logger()
    private val transactionId = AtomicLong(0)

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

    fun parseRequestParam(request: ServerRequest, key: String): String? =
        request.queryParam(key).orElse(null)

    fun identifyTransaction(): Long = transactionId.getAndIncrement()

    fun logRequest(transaction: Long, request: ServerRequest) {
        logger.info(
            "HTTP-Request-{} (path={}, param={}, method={}, type={}, length={})",
            transaction, request.path(), request.queryParams(), request.methodName(),
            request.headers().contentType().orElse(MediaType.ALL),
            request.headers().contentLength().orElse(0)
        )
    }

    fun convertException(throwable: Throwable): ResponseStatusException {
        val gourmetException = throwable as? GourmetException
        val nonNullStatus = gourmetException?.status ?: HttpStatus.INTERNAL_SERVER_ERROR
        val nonNullMessage = gourmetException?.message ?: "Unexpected server error"

        return ResponseStatusException(nonNullStatus, nonNullMessage, gourmetException ?: throwable)
    }
}
