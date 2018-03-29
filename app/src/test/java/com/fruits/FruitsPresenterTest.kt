package com.fruits

import android.util.Log
import com.fruits.fruits.FruitsPresenter
import com.fruits.repository.FruitRepository
import com.fruits.repository.SuccessfulResponseFruitRepository
import com.fruits.repository.UnSuccessfulResponseFruitRepository
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.util.SchedulerProvider
import com.fruits.util.TestLogger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito
import java.util.logging.Logger


class FruitsPresenterTest {

    /* val fruitItem = FruitItemApiResponse("apple", 10, 10)


    @Mock lateinit var view: FruitsPresenter.View

    private val fruitApiResponse: FruitsApiResponse = FruitsApiResponse(listOf(fruitItem))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(view.onRefreshAction()).willReturn(Observable.empty())
    }

    @Test
    fun successResultWithData() {

        val repo = SuccessfulResponseFruitRepository(listOf(fruitItem))

        val presenter = FruitsPresenter(view, repo, SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()))

        presenter.startPresenting()

        val expectedResponse = listOf(fruitItem)
        verify(view).showListFruits(expectedResponse)
    }
}*/
    val fruitItem = FruitItemApiResponse("apple", 10, 10)

    @Mock lateinit var view: FruitsPresenter.View
    //@Mock lateinit var logger: TestLogger

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        //log = Mockito.mock<Logger>(Logger::class.java)
        given(view.onRefreshAction()).willReturn(Observable.empty())
    }

    @Test
    fun successResultWithData() {
        //Given
        val repo = SuccessfulResponseFruitRepository(listOf(fruitItem))
        val presenter = FruitsPresenter(view, repo, SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()), TestLogger())

        //when
        presenter.startPresenting()

        //then
        val expectedResponse = listOf(fruitItem)
        verify(view).showListFruits(expectedResponse)
    }

    @Test
    fun givenApiCallWhenStartPresentingThenDisplayView() {
        //Given
        val repo = UnSuccessfulResponseFruitRepository("error")
        val presenter = FruitsPresenter(view, repo, SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()), TestLogger())

        //when(logger.logError("")
        //when
        presenter.startPresenting()

        //then
        verify(view).showError("An error occurs while loading the data, please try again.")
    }
}
