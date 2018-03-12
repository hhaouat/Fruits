package com.fruits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fruits.model.Fruit
import com.fruits.repository.remote.FruitItemApiResponse

class FruitListFragment : Fragment(), FruitsPresenter.View {

    lateinit var recyclerViewFruits: RecyclerView
    lateinit var fruitsPresenter: FruitsPresenter
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var fruitAdapter : FruitsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_fruits, container, false)
        recyclerViewFruits  = view.findViewById(R.id.recyclerview)

        fruitAdapter = FruitsAdapter(this.context)
        layoutManager = LinearLayoutManager(activity)

        recyclerViewFruits.setLayoutManager(layoutManager)
        recyclerViewFruits.setAdapter(fruitAdapter)

        fruitsPresenter = FruitsPresenter(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        fruitsPresenter.subscribeFruitService()
    }

    override fun showListFruits(listFruitApiResponse: List<FruitItemApiResponse>) {
        var listFruit : MutableList<Fruit> = ArrayList()
        for (fruitItemApiResponse in listFruitApiResponse){
            listFruit.add(Fruit(fruitItemApiResponse.type, fruitItemApiResponse.price, fruitItemApiResponse.weight))
        }
        fruitAdapter.update(listFruit)
    }
}