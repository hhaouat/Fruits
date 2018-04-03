package com.fruits.fruits

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fruits.R
import com.fruits.detail.DetailFragment
import com.fruits.model.Fruit
import com.fruits.repository.FruitRepository
import com.fruits.repository.FruitRepositoryImpl
import com.fruits.repository.remote.FruitItemApiResponse
import com.fruits.repository.remote.FruitsClient
import com.fruits.tracking.EventTracker
import com.fruits.util.AndroidLogger
import com.fruits.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FruitListFragment : Fragment(), FruitsPresenter.View, FruitsListItemClickListener{

    private lateinit var recyclerViewFruits: RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var fruitsPresenter: FruitsPresenter
    private lateinit var fruitsAdapter: FruitsAdapter
    private lateinit var fruitRepository : FruitRepository
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_fruits, container, false)
        recyclerViewFruits  = view.findViewById(R.id.recyclerview)

        fruitsAdapter = FruitsAdapter(this.context, this)
        layoutManager = LinearLayoutManager(activity)

        recyclerViewFruits.setLayoutManager(layoutManager)
        recyclerViewFruits.setAdapter(fruitsAdapter)

        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout)
        fruitRepository = FruitRepositoryImpl(FruitsClient.create())

        val scheduler = SchedulerProvider(AndroidSchedulers.mainThread(), Schedulers.io())

        fruitsPresenter = FruitsPresenter(this, fruitRepository, scheduler, AndroidLogger())

        return view
    }

    override fun onResume() {
        super.onResume()
        fruitsPresenter.startPresenting()
    }

    override fun onPause() {
        super.onPause()
        fruitsPresenter.stopPresenting()
    }

    override fun showListFruits(listFruitApiResponse: List<FruitItemApiResponse>) {
        val listFruit : MutableList<Fruit> = ArrayList()
        for (fruitItemApiResponse in listFruitApiResponse){
            listFruit.add(Fruit(fruitItemApiResponse.type, fruitItemApiResponse.price, fruitItemApiResponse.weight))
        }
        fruitsAdapter.update(listFruit)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this.activity, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.setRefreshing(isRefreshing)
    }

    override fun onRefreshAction(): Observable<Any> {
        return Observable.create<Any> { emitter ->
            swipeRefreshLayout.setOnRefreshListener {
                emitter.onNext(0) }
                emitter.setCancellable { swipeRefreshLayout.setOnRefreshListener(null) }
        }
    }

    override fun itemClicked(fruit: Fruit) {
        val detailFragment: Fragment = DetailFragment()
        val args = Bundle()
        args.putParcelable("fruit", fruit)
        detailFragment.arguments = args

        EventTracker.get().startTrackDisplayScreen(System.currentTimeMillis())

        activity.getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, detailFragment)
                .commitAllowingStateLoss()
    }
}