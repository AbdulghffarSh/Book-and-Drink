package com.abdulghffar.drink;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class  cartAdapter extends RecyclerView.Adapter <
        cartAdapter.ViewHolder >
{

    Context context;
    ArrayList < item > itemArrayList;
    ArrayList < item > cart;


    public cartAdapter (Context context, ArrayList < item > itemArrayList)
    {


        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @ Override
    public cartAdapter.
            ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {

        View v =
                LayoutInflater.from (context).inflate (R.layout.cart_item, parent,
                        false);



        return new ViewHolder (v);
    }

    @Override
    public void onBindViewHolder (@NonNull cartAdapter.ViewHolder holder,
                                  int position)
    {

        item item = itemArrayList.get (position);
        holder.itemName.setText (item.getItemName ());
        holder.itemPrice.setText (item.getItemPrice () + " JD");

        if (item.getItemPicURL () != null && !item.getItemPicURL ().isEmpty ())
        {
            Picasso.get ().load (item.getItemPicURL ()).
                    transform (new RoundedTransformation (30, 0)).placeholder (R.
                    drawable.
                    no_image).
                    error (R.drawable.no_image)
                    // To fit image into imageView
                    .fit ()
                    // To prevent fade animation
                    .noFade ().into (holder.itemImage);
        }
        else
        {
            holder.itemImage.setImageDrawable (ContextCompat.
                    getDrawable (context,
                            R.drawable.no_image));
        }

        holder.addButton.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {

                item selectedItem =
                        itemArrayList.get (holder.
                                getAdapterPosition
                                        ());
                System.out.println (selectedItem.
                        getItemName ());}
        });



    }

    @Override public int getItemCount ()
    {
        return itemArrayList.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemName, itemPrice;
        ImageView itemImage;
        ImageButton addButton;

        public ViewHolder (@NonNull View itemView)
        {
            super (itemView);

            itemName = itemView.findViewById (R.id.itemName);
            itemPrice = itemView.findViewById (R.id.itemPrice);
            itemImage = itemView.findViewById (R.id.img);
            addButton = itemView.findViewById (R.id.addButton);

        }
    }



}
