package com.fruits.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fruits.R
import com.fruits.model.Fruit

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.detail_fruit, container, false)

        val args = arguments
        val fruit: Fruit = args.getParcelable<Fruit>("fruit")

        val fruit_type = view.findViewById<TextView>(R.id.fruit_type)
        fruit_type.setText(fruit.type)

        val fruit_price: TextView = view.findViewById<TextView>(R.id.fruit_price);
        val unit_price: String = "p"
        fruit_price.setText(fruit.price.toString() + unit_price);

        val fruit_weight: TextView = view.findViewById<TextView>(R.id.fruit_weight);
        val unit_weight: String ="g"
        fruit_weight.setText(fruit.weight.toString()+ unit_weight);

        return view
    }
}