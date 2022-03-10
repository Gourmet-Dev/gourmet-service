package com.gourmet.service.common.mocker

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KFunction2

object TimeMocker {
    data class TimeDelta(val unit: Unit, val value: Long = 1) {
        enum class Unit(val operator: KFunction2<OffsetDateTime, Long, OffsetDateTime>) {
            SECOND(OffsetDateTime::plusSeconds),
            MINUTE(OffsetDateTime::plusMinutes),
            HOUR(OffsetDateTime::plusHours),
            DAY(OffsetDateTime::plusDays),
            MONTH(OffsetDateTime::plusMonths),
            YEAR(OffsetDateTime::plusYears)
        }
    }

    private val mockedOffsetDateTime: OffsetDateTime =
        OffsetDateTime.parse(
            "2021-01-01T12:34:56.789Z",
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
        )

    fun create(delta: TimeDelta? = null): OffsetDateTime {
        delta?.let {
            return it.unit.operator(mockedOffsetDateTime, it.value)
        }
        return mockedOffsetDateTime
    }
}
