package com.fruits.fruits;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fruits.R;
import com.fruits.fruits.FruitsListItemClickListener;
import com.fruits.model.Fruit;

import java.util.ArrayList;
import java.util.List;

public class FruitsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    List<Fruit> fruits = new ArrayList<>();

    private Context context;
    private FruitsListItemClickListener fruitClickListener;

    public FruitsAdapter(Context context, FruitsListItemClickListener fruitClickListener){
        this.context = context;
        this.fruitClickListener = fruitClickListener;
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
        final Fruit fruit = fruits.get(position);
        ((FruitViewHolder)holder).bind(fruit);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fruitClickListener.itemClicked(fruit);
            }
        });
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
        }
    }
}
