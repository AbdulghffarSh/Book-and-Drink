package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class cartAdapter extends RecyclerView.Adapter<
        cartAdapter.ViewHolder> {
    User user;
    Context context;
    ArrayList<item> itemArrayList;
    ArrayList<Integer> cartItemCount;
    FirebaseFirestore db;


    public cartAdapter(Context context, ArrayList<item> itemArrayList, ArrayList<Integer> cartItemCount) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        this.cartItemCount = cartItemCount;

    }


    @NonNull
    @Override
    public cartAdapter.
            ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent,
                false);



        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull cartAdapter.ViewHolder holder,
                                 int position) {
        item item = itemArrayList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice()+ " JD");
        holder.itemQuantity.setText(cartItemCount.get(position).toString());
        System.out.println(cartItemCount.get(position).toString());




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

        holder.plusButton.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {

                item selectedItem =
                        itemArrayList.get (holder.
                                getAdapterPosition
                                        ());

                 cartItemCount.set(position,cartItemCount.get(position));

                db = FirebaseFirestore.getInstance();



                db = FirebaseFirestore.getInstance();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {

                    DocumentReference docRef =
                            db.collection("Users").document(firebaseUser.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<
                            DocumentSnapshot>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void
                        onSuccess(DocumentSnapshot
                                          documentSnapshot) {


                            User user =
                                    documentSnapshot.
                                            toObject(User.class);

                            // if the user has cart


                            if (user.getCart() != null) {
                                Map<String, Integer> cart = user.getCart();
                                if (cart.containsKey(selectedItem.getItemID())) {
                                    cart.put(selectedItem.getItemID(), cart.get(item.getItemID()) + 1);
                                }
                                if (cart.get(item.getItemID())<1) {
                                    cart.remove(selectedItem.getItemID());
                                    user.setCart(cart);

                                }

                                db.
                                        collection("Users").document
                                        (firebaseUser.getUid
                                                ()).update("cart",
                                        cart);



                            }
                        }
                    });


                } else {

                }


                ;}
        });





    }


    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemQuantity;
        ImageView itemImage;
        Button plusButton, minusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.img);
            itemQuantity = itemView.findViewById(R.id.quantityy);

            plusButton = itemView.findViewById(R.id.plus);
            minusButton = itemView.findViewById(R.id.minus);
        }
    }


}
