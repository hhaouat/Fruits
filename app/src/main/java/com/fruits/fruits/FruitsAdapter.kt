package com.fruits.fruits;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fruits.R
import com.fruits.model.Fruit
import java.util.*

class FruitsAdapter(context: Context, fruitClickListener: FruitsListItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var fruits: MutableList<Fruit> = ArrayList()

    private val context: Context = context;
    private val fruitClickListener: FruitsListItemClickListener = fruitClickListener;

    @Override
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(context);
        val view: View = layoutInflater.inflate(R.layout.list_item_fruit, parent, false);

        val fruitViewHolder = FruitViewHolder(view);

        return fruitViewHolder;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val fruit: Fruit = fruits.get(position);
        (holder as FruitViewHolder).bind(fruit);

        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            fruitClickListener.itemClicked(fruit);
        });
    }

    @Override
    override fun getItemCount(): Int = fruits.size

    fun update(fruits: MutableList<Fruit>) {
        this.fruits.clear();
        this.fruits.addAll(fruits);
        notifyDataSetChanged();
    }

    companion object {
        class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(fruit: Fruit) {
                val fruit_type: TextView = itemView.findViewById(R.id.fruit_type);
                fruit_type.setText(fruit.type);
            }
        }
    }
}
