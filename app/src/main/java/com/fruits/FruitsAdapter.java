package com.fruits;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fruits.model.Fruit;

import java.util.ArrayList;
import java.util.List;

public class FruitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    List<Fruit> fruits = new ArrayList<>();

    private Context context;

    public FruitsAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_fruit, parent, false);

        FruitViewHolder fruitViewHolder = new FruitViewHolder(view);

        return fruitViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        ((FruitViewHolder)holder).bind(fruit);
    }

    @Override
    public int getItemCount() { return fruits.size();}

    public void update(List<Fruit> fruits) {
        this.fruits.clear();
        this.fruits.addAll(fruits);
        notifyDataSetChanged();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder {

        public FruitViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Fruit fruit){
            TextView fruit_type = itemView.findViewById(R.id.fruit_type);
            fruit_type.setText(fruit.getType());

            TextView fruit_price = itemView.findViewById(R.id.fruit_price);
            fruit_price.setText(String.valueOf(fruit.getPrice()));

            TextView fruit_weight = itemView.findViewById(R.id.fruit_weight);
            fruit_weight.setText(String.valueOf(fruit.getWeight()));
        }
    }
}
