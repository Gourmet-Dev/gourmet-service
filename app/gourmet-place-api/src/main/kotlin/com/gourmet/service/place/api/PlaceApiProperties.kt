package com.gourmet.service.place.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class PlaceApiProperties(
    @Value("\${gourmet.place.api.root}")
    val root: String
)
