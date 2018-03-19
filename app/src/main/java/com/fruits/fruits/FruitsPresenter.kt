package com.fruits.fruits

import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.fruits.BasePresenter
import com.fruits.repository.FruitRepository
import com.fruits.repository.remote.FruitItemApiResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FruitsPresenter(val view: View, val fruitRepository: FruitRepository) : BasePresenter(){

    private val TAG = FruitsPresenter::class.java.getName()

    fun startPresenting() {
        fruitRepository.getFruits(object :  FruitRepository.FruitRepositoryCallback {
            override fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>) {
                view.showListFruits(listFruitApiResponse)
            }

            override fun onError(message: String) {
                Log.e(TAG, "Error subscribe" + message)
                view.showError("An error occurs while loading the data, please try again.")
            }
        })
        addToUnsubscribe(
                view.onRefreshAction()
                        .doOnNext({ ignored -> view.showRefreshing(true) })
                        .switchMapSingle({ ignored -> fruitRepository.getSingleFruits() })
                        .map { it.fruit }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
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
                                }))
    }

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