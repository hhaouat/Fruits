package com.fruits

import com.fruits.repository.remote.FruitsApiResponse
import com.fruits.repository.remote.FruitsClient

class FruitsPresenter() : BasePresenter<FruitsPresenter.View>() {

    val fruitClient by lazy {
        FruitsClient.create()
    }

    lateinit var view: View

    override fun register(view: View) {
        super.register(view)
        this.view = view
    }

    interface View : BasePresenterView {
    }
}