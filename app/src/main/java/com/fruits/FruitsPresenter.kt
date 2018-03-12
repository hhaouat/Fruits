package com.fruits

import com.fruits.repository.FruitRepository
import com.fruits.repository.remote.FruitItemApiResponse

class FruitsPresenter(view: View) : BasePresenter<FruitsPresenter.View>(), FruitRepository.FruitRepositoryCallback {
    var view = view
    var repository = FruitRepository()

    override fun register(view: View) {
        super.register(view)
    }

    fun subscribeFruitService() {
        repository.getFruits(this)
    }

    interface View : BasePresenterView {
        fun showListFruits(fruitItemApiResponse: List<FruitItemApiResponse>)
    }

    override fun onSuccess(listFruitApiResponse: List<FruitItemApiResponse>) {
       view.showListFruits(listFruitApiResponse)
    }

    override fun onError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}