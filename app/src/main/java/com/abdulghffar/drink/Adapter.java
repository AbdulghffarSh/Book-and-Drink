package com.abdulghffar.drink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<item> itemArrayList;

    public Adapter(Context context, ArrayList<item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        item item = itemArrayList.get(position);


        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());




    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPrice,itemDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                    itemName= itemView.findViewById(R.id.itemName);
                    itemPrice= itemView.findViewById(R.id.itemPrice);

        }
    }

}
