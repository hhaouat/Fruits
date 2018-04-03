package com.fruits

import com.fruits.fruits.FruitListFragment
import com.fruits.fruits.FruitsPresenter
import com.fruits.repository.SuccessfulResponseFruitRepository
import com.fruits.repository.UnSuccessfulResponseFruitRepository
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.util.SchedulerProvider
import com.fruits.util.TestLogger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class FruitsPresenterTest {
    val fruitItem = FruitItemApiResponse("apple", 10, 10)

    @Mock val view: FruitsPresenter.View = FruitListFragment()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(view.onRefreshAction()).willReturn(Observable.empty())
    }

    @Test
    fun whenStartPresentingThenSuccessDisplayView() {
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
    fun whenStartPresentingThenErrorDisplayView() {
        //Given
        val repo = UnSuccessfulResponseFruitRepository("error")
        val presenter = FruitsPresenter(view, repo, SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()), TestLogger())

        //when
        presenter.startPresenting()

        //then
        verify(view).showError("An error occurs while loading the data, please try again.")
    }
}
