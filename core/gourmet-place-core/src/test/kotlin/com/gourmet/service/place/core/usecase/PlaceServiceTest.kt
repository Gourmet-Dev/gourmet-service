package com.gourmet.service.place.core.usecase

import com.gourmet.service.common.helper.TestUtils
import com.gourmet.service.place.core.domain.Place
import com.gourmet.service.place.core.mocker.PlaceMocker
import com.gourmet.service.place.core.usecase.dto.GetAllPlacesData
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.test.StepVerifier

class PlaceServiceTest : BehaviorSpec() {
    private val manyCount = (0..255).random()

    private val emptyPlaceList = listOf<Place>()
    private val singlePlaceList = listOf(
        PlaceMocker.create()
    )
    private val manyPlaceList = listOf(
        *(Array(manyCount) { PlaceMocker.create() })
    )

    private val emptyPlaceFlux = TestUtils.listToFlux(emptyPlaceList)
    private val singlePlaceFlux = TestUtils.listToFlux(singlePlaceList)
    private val manyPlaceFlux = TestUtils.listToFlux(manyPlaceList)

    private val emptyGetAllPlacesDataList = emptyPlaceList.map { GetAllPlacesData.fromPlace(it) }
    private val singleGetAllPlacesDataList = singlePlaceList.map { GetAllPlacesData.fromPlace(it) }
    private val manyGetAllPlacesDataList = manyPlaceList.map { GetAllPlacesData.fromPlace(it) }

    init {
        val placeRepository = mockk<PlaceRepository>()
        val service = PlaceService(placeRepository)

        afterContainer {
            clearAllMocks()
        }

        Given("저장된 장소가 없는 상황에서") {
            every { placeRepository.getAllPlaces() } returns emptyPlaceFlux.log()
            When("모든 장소 데이터를 요청하면") {
                val result = StepVerifier.create(service.getAllPlaces())
                Then("장소 데이터베이스를 조회해야 한다") {
                    verify(exactly = 1) {
                        placeRepository.getAllPlaces()
                    }
                }
                Then("조회된 장소 데이터는 비어 있어야 한다") {
                    result
                        .expectSubscription()
                        .expectNext(*emptyGetAllPlacesDataList.toTypedArray())
                        .expectComplete()
                        .verify()
                }
            }
        }
        Given("저장된 장소가 1개 있는 상황에서") {
            every { placeRepository.getAllPlaces() } returns singlePlaceFlux.log()
            When("모든 장소 데이터를 요청하면") {
                val result = StepVerifier.create(service.getAllPlaces())
                Then("장소 데이터베이스를 조회해야 한다") {
                    verify(exactly = 1) {
                        placeRepository.getAllPlaces()
                    }
                }
                Then("조회된 장소 데이터에 1개의 장소가 존재해야 한다") {
                    result
                        .expectSubscription()
                        .expectNext(*singleGetAllPlacesDataList.toTypedArray())
                        .expectComplete()
                        .verify()
                }
            }
        }
        Given("저장된 장소가 N개 있는 상황에서") {
            every { placeRepository.getAllPlaces() } returns manyPlaceFlux.log()
            When("모든 장소 데이터를 요청하면") {
                val result = StepVerifier.create(service.getAllPlaces())
                Then("장소 데이터베이스를 조회해야 한다") {
                    verify(exactly = 1) {
                        placeRepository.getAllPlaces()
                    }
                }
                Then("조회된 장소 데이터에 N개의 장소가 존재해야 한다") {
                    result
                        .expectSubscription()
                        .expectNext(*manyGetAllPlacesDataList.toTypedArray())
                        .expectComplete()
                        .verify()
                }
            }
        }
    }
}
