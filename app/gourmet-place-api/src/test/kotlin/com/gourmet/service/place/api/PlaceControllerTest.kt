package com.gourmet.service.place.api

import com.gourmet.service.common.helper.TestUtils
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.mocker.PlaceMocker
import com.gourmet.service.place.core.usecase.PlaceService
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesData
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class PlaceControllerTest : DescribeSpec() {
    private val manyCount = (0..255).random()

    private val emptyPlaceList = listOf<Place>()
    private val singlePlaceList = listOf(
        PlaceMocker.create()
    )
    private val manyPlaceList = listOf(
        *(Array(manyCount) { PlaceMocker.create() })
    )

    private val emptyGetAllPlacesDataFlux = TestUtils.listToFlux(
        emptyPlaceList.map { GetAllPlacesData.fromPlace(it) }
    )
    private val singleGetAllPlacesDataFlux = TestUtils.listToFlux(
        singlePlaceList.map { GetAllPlacesData.fromPlace(it) }
    )
    private val manyGetAllPlacesDataFlux = TestUtils.listToFlux(
        manyPlaceList.map { GetAllPlacesData.fromPlace(it) }
    )

    private val emptyPlaceResponseBody = TestUtils.listToJson(
        emptyPlaceList.map { GetAllPlacesData.fromPlace(it) }
    )
    private val singlePlaceResponseBody = TestUtils.listToJson(
        singlePlaceList.map { GetAllPlacesData.fromPlace(it) }
    )
    private val manyPlaceResponseBody = TestUtils.listToJson(
        manyPlaceList.map { GetAllPlacesData.fromPlace(it) }
    )

    init {
        val placeService = mockk<PlaceService>()
        val webTestClient = WebTestClient
            .bindToController(PlaceController(placeService))
            .build()

        afterContainer {
            clearAllMocks()
        }

        describe("getAllPlacesė„¼") {
            val performRequest = { webTestClient.get().uri("/place").exchange() }
            context("ģ ģ„ė ė°ģ“ķ°ź° ģė ģķ©ģģ ģģ²­ķ ź²½ģ°") {
                every { placeService.getAllPlaces() } returns emptyGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("ģė¹ģ¤ė„¼ ķµķ“ ė°ģ“ķ°ė„¼ ģ”°ķķė¤") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces()
                    }
                }
                it("ģģ²­ģ ģ±ź³µķė¤") {
                    response.expectStatus().isOk
                }
                it("ė°ķ ķģģ JSONģ“ė¤") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSONģ ė¹ģ“ ģė¤") {
                    response.expectBody().json(emptyPlaceResponseBody)
                }
            }
            context("ģ ģ„ė ė°ģ“ķ°ź° 1ź°ģø ģķ©ģģ ģģ²­ķ ź²½ģ°") {
                every { placeService.getAllPlaces() } returns singleGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("ģė¹ģ¤ė„¼ ķµķ“ ė°ģ“ķ°ė„¼ ģ”°ķķė¤") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces()
                    }
                }
                it("ģģ²­ģ ģ±ź³µķė¤") {
                    response.expectStatus().isOk
                }
                it("ė°ķ ķģģ JSONģ“ė¤") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSONģė ė°ģ“ķ° 1ź°ź° ģė¤") {
                    response.expectBody().json(singlePlaceResponseBody)
                }
            }
            context("ģ ģ„ė ė°ģ“ķ°ź° Nź°ģø ģķ©ģģ ģģ²­ķ ź²½ģ°") {
                every { placeService.getAllPlaces() } returns manyGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("ģė¹ģ¤ė„¼ ķµķ“ ė°ģ“ķ°ė„¼ ģ”°ķķė¤") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces()
                    }
                }
                it("ģģ²­ģ ģ±ź³µķė¤") {
                    response.expectStatus().isOk
                }
                it("ė°ķ ķģģ JSONģ“ė¤") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSONģė ė°ģ“ķ° Nź° ģė¤") {
                    response.expectBody().json(manyPlaceResponseBody)
                }
            }
        }
    }
}
