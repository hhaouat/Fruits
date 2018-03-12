package com.fruits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fruits.model.Fruit
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FruitListFragment : Fragment(), FruitsPresenter.View {

    lateinit var recyclerViewFruits: RecyclerView
    lateinit var fruitPresenter: FruitsPresenter
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var fruitAdapter : FruitsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_fruits, container, false)

        fruitAdapter = FruitsAdapter(this.context)
        layoutManager       = LinearLayoutManager(activity)

        recyclerViewFruits  = view.findViewById(R.id.recyclerview)
        recyclerViewFruits.setLayoutManager(layoutManager)
        recyclerViewFruits.setAdapter(fruitAdapter)

        fruitPresenter  = FruitsPresenter()
        val fruitClient = fruitPresenter.fruitClient
        val fruits = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                if (response.isSuccessful) {
                    showListFruits(response.body()!!.fruit)
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())
                // Log error here since request failed
            }
        })
        return view
    }
    override fun showListFruits(listFruitApiResponse: List<FruitItemApiResponse>) {
        var listFruit : MutableList<Fruit> = ArrayList()
        for (fruitItemApiResponse in listFruitApiResponse){
            listFruit.add(Fruit(fruitItemApiResponse.type, fruitItemApiResponse.price, fruitItemApiResponse.weight))
        }
        fruitAdapter.update(listFruit)
    }
}