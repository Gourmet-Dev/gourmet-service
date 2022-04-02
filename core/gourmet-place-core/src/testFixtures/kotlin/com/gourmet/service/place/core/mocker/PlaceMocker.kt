package com.gourmet.service.place.core.mocker

import com.gourmet.service.common.mocker.GeospatialPointMocker
import com.gourmet.service.common.mocker.TimeMocker
import com.gourmet.service.place.core.domain.Place

object PlaceMocker {
    private var counter: Long = 1

    private fun fetchAndIncreaseCounter(): Long {
        val current = counter
        counter += 1
        return current
    }

    fun create(id: Long = fetchAndIncreaseCounter()): Place {
        return Place(
            id.toString(),
            "MockedPlace$id",
            "MockedAddress$id",
            "MockedThumbnail$id",
            listOf("MockedImage$id"),
            GeospatialPointMocker.create(),
            listOf("MockedTag$id"),
            TimeMocker.create(TimeMocker.TimeDelta(TimeMocker.TimeDelta.Unit.DAY, id.unaryMinus())),
            TimeMocker.create()
        )
    }
}
