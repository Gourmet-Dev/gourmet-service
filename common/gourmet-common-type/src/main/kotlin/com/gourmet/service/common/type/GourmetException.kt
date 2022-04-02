package com.gourmet.service.common.type

import org.springframework.http.HttpStatus

data class GourmetException(
    override val message: String,
    val status: HttpStatus,
    val pkgname: String,
    val clsname: String
) : Exception(message)
