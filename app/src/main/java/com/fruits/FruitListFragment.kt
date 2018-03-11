package com.fruits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fruits.repository.remote.FruitsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FruitListFragment : Fragment() {

    lateinit var recyclerViewFruits: RecyclerView
    lateinit var fruitPresenter: FruitsPresenter
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_fruits, container, false)

        layoutManager       = LinearLayoutManager(activity)
        recyclerViewFruits  = view.findViewById(R.id.recyclerview)
        recyclerViewFruits.setLayoutManager(layoutManager)

        fruitPresenter  = FruitsPresenter()
        val fruitClient = fruitPresenter.fruitClient
        val fruits = fruitClient.getFruit()

        fruits.enqueue(object : Callback<FruitsApiResponse> {
            override fun onResponse(call: Call<FruitsApiResponse>, response: Response<FruitsApiResponse>) {
                if (response.isSuccessful) {
                    for (fruit in response.body()!!.fruit){
                        println("fruit: "+ fruit.type)
                    }
                }
            }
            override fun onFailure(call: Call<FruitsApiResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.toString())
                // Log error here since request failed
            }
        })
        return view
    }
}