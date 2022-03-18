package com.gourmet.service.common.helper

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object OffsetDateTimeUtils {
    @ReadingConverter
    class ReadStringifiedOffsetDateTime : Converter<String, OffsetDateTime> {
        override fun convert(value: String): OffsetDateTime {
            return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    @WritingConverter
    class WriteStringifiedOffsetDateTime : Converter<OffsetDateTime, String> {
        override fun convert(value: OffsetDateTime): String {
            return value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }
}
