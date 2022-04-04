package com.gourmet.service.common.helper

import com.gourmet.service.common.type.GourmetException
import org.springframework.http.HttpStatus

inline fun <reified T : Any> T.exception(message: String? = null, status: HttpStatus? = null): GourmetException {
    val nonNullMessage = message ?: "exception has raised on '${T::class.java.canonicalName}'"
    val nonNullStatus = status ?: HttpStatus.INTERNAL_SERVER_ERROR
    return GourmetException(nonNullMessage, nonNullStatus, T::class.java.packageName, T::class.java.canonicalName)
}
