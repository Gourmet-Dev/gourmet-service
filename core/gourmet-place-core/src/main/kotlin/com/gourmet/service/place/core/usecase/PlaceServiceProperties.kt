package com.gourmet.service.place.core.usecase

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class PlaceServiceProperties(
    @Value("\${gourmet.place.service.repository}")
    val repository: String
)
