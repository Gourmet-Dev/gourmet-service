package com.gourmet.service.common.helper

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}
