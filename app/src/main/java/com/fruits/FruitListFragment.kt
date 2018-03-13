package com.fruits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fruits.detail.DetailFragment
import com.fruits.fruits.FruitsListItemClickListener
import com.fruits.model.Fruit
import com.fruits.repository.remote.FruitItemApiResponse

class FruitListFragment : Fragment(), FruitsPresenter.View, FruitsListItemClickListener{

    lateinit var recyclerViewFruits: RecyclerView
    lateinit var fruitsPresenter: FruitsPresenter
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var fruitsAdapter: FruitsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_fruits, container, false)
        recyclerViewFruits  = view.findViewById(R.id.recyclerview)

        fruitsAdapter = FruitsAdapter(this.context, this)
        layoutManager = LinearLayoutManager(activity)

        recyclerViewFruits.setLayoutManager(layoutManager)
        recyclerViewFruits.setAdapter(fruitsAdapter)

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
        fruitsAdapter.update(listFruit)
    }

    override fun itemClicked(fruit: Fruit) {
        val detailFragment: Fragment = DetailFragment()
        val args = Bundle()
        args.putParcelable("fruit", fruit)
        detailFragment.arguments = args

        activity.getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, detailFragment)
                .commitAllowingStateLoss()
    }
}