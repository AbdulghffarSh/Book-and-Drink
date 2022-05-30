package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<item> itemArrayList;
    ArrayList<item> cart;
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

                                                              if(user.getCart() == null){
                                                                  Map<String,Integer> EmptyMap = new Map<String, Integer>();

                                                                  user.setCart(EmptyMap);

                                                              }

                                                              if (user.getCart() != null) {

                                                                Map<String , Integer> cart = user.getCart();
                                                                if(cart.containsKey(item.getItemID())){
                                                                    int x = cart.get(item.getItemID()) + 1;

                                                                    cart.put(item.getItemID(), x);


                                                                }
                                                                  if(!cart.containsKey(item.getItemID())){
//                                                                      cart.put(item.getItemID(), 1);
                                                                      cart.put("test",15);

                                                                      System.out.println(cart);
                                                                  }

                                                              }

                                                          }
                                                      }
                    );


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
