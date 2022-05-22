package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private ArrayList<item> drinks;

    public Adapter(ArrayList<item> drinks) {
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(drinks.get(position).getItemName());
        holder.itemPrice.setText(Float.toString(drinks.get(position).getItemPrice()));
        holder.itemDescription.setText(drinks.get(position).getItemPicURL());
    }

    @Override
    public int getItemCount() {
        return (drinks != null) ? drinks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice, itemDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemDescription = itemView.findViewById(R.id.itemDescription);
        }
    }
}
