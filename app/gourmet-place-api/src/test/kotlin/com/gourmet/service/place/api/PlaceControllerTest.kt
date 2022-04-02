package com.gourmet.service.place.api

import com.gourmet.service.common.helper.TestUtils
import com.gourmet.service.place.api.payload.GetAllPlacesResponse
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
        emptyPlaceList.map { GetAllPlacesResponse.fromPlace(it) }
    )
    private val singlePlaceResponseBody = TestUtils.listToJson(
        singlePlaceList.map { GetAllPlacesResponse.fromPlace(it) }
    )
    private val manyPlaceResponseBody = TestUtils.listToJson(
        manyPlaceList.map { GetAllPlacesResponse.fromPlace(it) }
    )

    init {
        val placeService = mockk<PlaceService>()
        val webTestClient = WebTestClient
            .bindToController(PlaceController(placeService))
            .build()

        afterContainer {
            clearAllMocks()
        }

        describe("getAllPlaces를") {
            val performRequest = { webTestClient.get().uri("/place").exchange() }
            context("저장된 데이터가 없는 상황에서 요청한 경우") {
                every { placeService.getAllPlaces(any()) } returns emptyGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("서비스를 통해 데이터를 조회한다") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces(any())
                    }
                }
                it("요청은 성공한다") {
                    response.expectStatus().isOk
                }
                it("반환 형식은 JSON이다") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSON은 비어 있다") {
                    response.expectBody().json(emptyPlaceResponseBody)
                }
            }
            context("저장된 데이터가 1개인 상황에서 요청한 경우") {
                every { placeService.getAllPlaces(any()) } returns singleGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("서비스를 통해 데이터를 조회한다") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces(any())
                    }
                }
                it("요청은 성공한다") {
                    response.expectStatus().isOk
                }
                it("반환 형식은 JSON이다") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSON에는 데이터 1개가 있다") {
                    response.expectBody().json(singlePlaceResponseBody)
                }
            }
            context("저장된 데이터가 N개인 상황에서 요청한 경우") {
                every { placeService.getAllPlaces(any()) } returns manyGetAllPlacesDataFlux.log()
                val response = performRequest()

                it("서비스를 통해 데이터를 조회한다") {
                    verify(exactly = 1) {
                        placeService.getAllPlaces(any())
                    }
                }
                it("요청은 성공한다") {
                    response.expectStatus().isOk
                }
                it("반환 형식은 JSON이다") {
                    response.expectHeader().contentType(MediaType.APPLICATION_JSON)
                }
                it("JSON에는 데이터 N개 있다") {
                    response.expectBody().json(manyPlaceResponseBody)
                }
            }
        }
    }
}
