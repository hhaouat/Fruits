package com.fruits.fruits

import android.util.Log
import com.fruits.BasePresenter
import com.fruits.repository.FruitRepository
import com.fruits.repository.remote.FruitItemApiResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FruitsPresenter(view: View) : BasePresenter<FruitsPresenter.View>(), FruitRepository.FruitRepositoryCallback {
    var view = view
    var repository = FruitRepository()

    private val TAG = FruitsPresenter::class.java!!.getName()

    override fun register(view: View) {
        super.register(view)
    }

    fun subscribeFruitService() {
        repository.getFruits(this)
        loadFruitsOnRefreshAction(view)
    }

    fun loadFruitsOnRefreshAction(view: View) {
        addToUnsubscribe(
                view.onRefreshAction()
                .doOnNext({ ignored -> view.showRefreshing(true) })
                .switchMapSingle({ ignored -> repository.getSingleFruits() })
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

    interface View : BasePresenterView {
        fun showListFruits(fruitItemApiResponse: List<FruitItemApiResponse>)
        fun showError(error: String)
        fun showRefreshing(isRefreshing: Boolean)
        fun onRefreshAction(): Observable<Any>
    }

    override fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>) {
       view.showListFruits(listFruitApiResponse)
    }

    override fun onError(errorMessage: String) {
        Log.e(TAG, "Error subscribe" + errorMessage)
        view.showError("An error occurs while loading the data, please try again.")
    }
}