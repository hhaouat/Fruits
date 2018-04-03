package com.fruits.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fruits.R
import com.fruits.model.Fruit
import com.fruits.tracking.EventTracker
import com.fruits.util.AndroidLogger

class DetailFragment : Fragment() {

    val detailPresenter: DetailPresenter = DetailPresenter()
    val logger: AndroidLogger = AndroidLogger()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.detail_fruit, container, false)

        val args = arguments
        val fruit: Fruit = args.getParcelable<Fruit>("fruit")
        val fruit_price: TextView = view.findViewById<TextView>(R.id.fruit_price);
        val fruit_weight: TextView = view.findViewById<TextView>(R.id.fruit_weight);
        val fruit_type = view.findViewById<TextView>(R.id.fruit_type)

        val unit_price = "p"
        val unit_weight = "g"

        fruit_price.setText(fruit.price.toString() + unit_price);
        fruit_weight.setText(fruit.weight.toString() + unit_weight);

        fruit_type.setText(fruit.type)
        return view
    }

    override fun onStart() {
        super.onStart()
        EventTracker.get().endTrackDisplayScreen(System.currentTimeMillis())

        detailPresenter.trackUserInteractionRequest()
        logger.logInfo(DetailFragment::class.java.name, "Time tracking user click " + EventTracker.get().calculTrackDisplayScreen() + "ms")
    }
}