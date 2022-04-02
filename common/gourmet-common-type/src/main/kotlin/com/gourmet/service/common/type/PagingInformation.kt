package com.gourmet.service.common.type

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux

data class PagingInformation(
    val page: Long,
    val size: Long
) {
    fun <T : Any> paginateFlux(flux: Flux<T>): Flux<T> =
        flux.skip(page * size).take(size)

    fun toPageable(): Pageable =
        PageRequest.of(page.toInt(), size.toInt())
}
