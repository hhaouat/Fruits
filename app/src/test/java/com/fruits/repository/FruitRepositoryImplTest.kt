package com.fruits.repository

import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsClient
import com.fruits.tracking.EventTracker
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FruitRepositoryImplTest {
    @Mock val fruitClient: FruitsClient = FruitsClient.create()

    @Mock lateinit var callback : FruitRepository.FruitRepositoryCallback

    val fruitItem = FruitItemApiResponse("apple", 10, 10)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun whenGetSingleFruitsThenGetSingleFruitApiResponse() {
        //given
        val repositoryImpl = FruitRepositoryImpl(fruitClient)

        //when
        repositoryImpl.getSingleFruits()

        //then
        Mockito.verify(fruitClient).getSingleFruitApiResponse()
    }

    @Test
    fun whenTrackCompleteRequestThenSentEventLoad() {
        //given
        val repositoryImpl = FruitRepositoryImpl(fruitClient)

        //when
        repositoryImpl.trackCompleteRequest()

        //then
        Mockito.verify(fruitClient).sentEventLoad("load", EventTracker.timeCompleteRequest.toInt())
    }

    @Test
    fun whenTrackErrorRequestThenSentEventError() {
        //given
        val repositoryImpl = FruitRepositoryImpl(fruitClient)

        //when
        repositoryImpl.trackErrorRequest("error")

        //then
        Mockito.verify(fruitClient).sentEventError("error","error")
    }

    @Test
    fun whenTrackUserInteractionRequestThenSentEventDisplay() {
        //given
        val repositoryImpl = FruitRepositoryImpl(fruitClient)

        //when
        repositoryImpl.trackUserInteractionRequest(EventTracker.get().calculTrackDisplayScreen())

        //then
        Mockito.verify(fruitClient).sentEventDisplay("display", EventTracker.get().calculTrackDisplayScreen().toString())
    }

    @Test
    fun whenGetFruitsThenCallbackOnSuccess() {
        //given
        val repo = SuccessfulResponseFruitRepository(listOf(fruitItem))

        //when
        //repositoryImpl.getFruits(callback)
        repo.getFruits(callback);

        //then
        Mockito.verify(callback).onSuccess(listOf(fruitItem))
    }

    @Test
    fun whenGetFruitsThenCallbackOnError() {
        //given
        val repo = UnSuccessfulResponseFruitRepository("error")

        //when
        repo.getFruits(callback);

        //then
        Mockito.verify(callback).onError("error")
    }

}