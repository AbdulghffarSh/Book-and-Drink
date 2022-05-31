package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<item> itemArrayList;
    Map < item, Integer > cart;
    FirebaseFirestore db;


    public Adapter(Context context, ArrayList<item> itemArrayList) {


        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {

        View v =
                LayoutInflater.from(context).inflate(R.layout.item, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder,
                                 int position) {

        item item = itemArrayList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice() + " JD");

        if (item.getItemPicURL() != null && !item.getItemPicURL().isEmpty()) {
            Picasso.get().load(item.getItemPicURL()).
                    transform(new RoundedTransformation(30, 0)).placeholder(R.
                    drawable.
                    no_image).
                    error(R.drawable.no_image)
                    // To fit image into imageView
                    .fit()
                    // To prevent fade animation
                    .noFade().into(holder.itemImage);
        } else {
            holder.itemImage.setImageDrawable(ContextCompat.
                    getDrawable(context,
                            R.drawable.no_image));
        }

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();

                item selectedItem =
                        itemArrayList.get(holder.
                                getAdapterPosition
                                        ());


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

                            System.out.println(user.getCart());

                            if (user.getCart() == null) {
                                Map<String, Integer> newCart = new HashMap<>();
                                user.setCart(newCart);
                                System.out.println("Cart is Null\nCreated one");


                            }

                            if (user.getCart() != null) {
                                System.out.println("User has Map");
                                Map<String, Integer> cart = user.getCart();
                                if (cart.containsKey(selectedItem.getItemID())) {
                                    cart.put(selectedItem.getItemID(), cart.get(item.getItemID()) + 1);
                                }
                                if (!cart.containsKey(item.getItemID())) {
                                    cart.put(selectedItem.getItemID(), 1);
                                    user.setCart(cart);
                                    System.out.println(user.getCart());

                                }

                                db.
                                        collection("Users").document
                                        (firebaseUser.getUid
                                                ()).update("cart",
                                        cart);


                                Toasty.custom(context, "Item added to cart", ResourcesCompat.getDrawable(v.getResources(), R.drawable.cart, null),
                                        Color.WHITE,Color.parseColor("#0A2658"), Toasty.LENGTH_SHORT, true, true).show();
                            }
                        }
                    });


                    } else {

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice;
        ImageView itemImage;
        ImageButton addButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.img);
            addButton = itemView.findViewById(R.id.addButton);

        }
    }


}
