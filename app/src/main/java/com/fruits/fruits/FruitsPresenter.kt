package com.fruits.fruits

import android.util.Log
import com.fruits.BasePresenter
import com.fruits.repository.FruitRepository
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.util.AndroidLogger
import com.fruits.util.Logger
import com.fruits.util.SchedulerProvider
import io.reactivex.Observable

class FruitsPresenter(val view: View, val fruitRepository: FruitRepository, val scheduler: SchedulerProvider, val logger: Logger) : BasePresenter() {

    private val TAG = FruitsPresenter::class.java.getName()

    fun startPresenting() {
        fruitRepository.getFruits(callbackFruits)
        addToUnsubscribe(disposableOnrefreshAction)
    }

    private val callbackFruits = object : FruitRepository.FruitRepositoryCallback {
        override fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>) {
            view.showListFruits(listFruitApiResponse)
        }

        override fun onError(logMessage: String) {
            //Log.e(TAG, "Error subscribe" + logMessage)
            logger.logError(logMessage)
            view.showError("An error occurs while loading the data, please try again.")
        }
    }

    private val disposableOnrefreshAction = view.onRefreshAction()
        .doOnNext({ ignored -> view.showRefreshing(true) })
        .switchMapSingle({ ignored -> fruitRepository.getSingleFruits() })
        .map { it.fruit }
        .subscribeOn(scheduler.ioScheduler)
        .observeOn(scheduler.uiScheduler)
        .subscribe(
            { fruits ->
                Log.i(TAG, "Subscribe fruits")
                view.showRefreshing(false)
                view.showListFruits(fruits)
            },
            { error ->
                Log.e(TAG, "Error subscribe" + error)
                view.showError("An error occurs while loading the data, please try again.")
                view.showRefreshing(false)
            })

    fun stopPresenting() {
        unregister()
    }

    interface View {
        fun showListFruits(fruitItemApiResponse: List<FruitItemApiResponse>)
        fun showError(error: String)
        fun showRefreshing(isRefreshing: Boolean)
        fun onRefreshAction(): Observable<Any>
    }
}