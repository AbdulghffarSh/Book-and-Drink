package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

public class cartAdapter extends RecyclerView.Adapter<
        cartAdapter.ViewHolder> {
    User user;
    Context context;
    ArrayList<item> itemArrayList;
    Map<String, Integer> cart;
    FirebaseFirestore db;
    List<Integer> quantity;
    TextView total;
    Context fragmentContext;


    public cartAdapter(Context context, ArrayList<item> itemArrayList, Map<String, Integer> cart, Context fragmentContext) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        this.cart = cart;
        this.fragmentContext = fragmentContext;

    }


    @NonNull
    @Override
    public cartAdapter.
            ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        quantity = new ArrayList<>(cart.values());

        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent,
                false);

        calculateTotal();

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull cartAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        item item = itemArrayList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice() + " JD");
        holder.itemQuantity.setText(quantity.get(position).toString());


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

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item selectedItem =
                        itemArrayList.get(holder.
                                getAdapterPosition
                                        ());

                quantity.set(position, quantity.get(position));

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


                            assert user != null;
                            System.out.println((selectedItem.getItemName()));
                            user.getCart().put(selectedItem.getItemID(), user.getCart().get(selectedItem.getItemID()) + 1);
                            quantity.set(holder.getAdapterPosition(), quantity.get(holder.getAdapterPosition()) + 1);
                            notifyDataSetChanged();
                            calculateTotal();

                            db.collection("Users").document(user.getuID())
                                    .update(
                                            "cart", user.getCart()
                                    );

                        }
                    });


                } else {

                }


                ;
            }
        });


        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    item selectedItem =
                            itemArrayList.get(holder.
                                    getAdapterPosition
                                            ());

                    quantity.set(position, quantity.get(position));

                    db = FirebaseFirestore.getInstance();


                    db = FirebaseFirestore.getInstance();
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {

                        DocumentReference docRef =
                                db.collection("Users").document(firebaseUser.getUid());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<
                                DocumentSnapshot>() {
                            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                            @Override
                            public void
                            onSuccess(DocumentSnapshot
                                              documentSnapshot) {


                                User user =
                                        documentSnapshot.
                                                toObject(User.class);

                                // if the user has cart


                                assert user != null;
                                System.out.println((selectedItem.getItemName()));
                                user.getCart().put(selectedItem.getItemID(), user.getCart().get(selectedItem.getItemID()) - 1);
                                quantity.set(holder.getAdapterPosition(), quantity.get(holder.getAdapterPosition()) - 1);

                                if (user.getCart().get(selectedItem.getItemID()) < 1) {
                                    user.getCart().remove(selectedItem.getItemID());
                                    itemArrayList.remove(selectedItem);
                                }
                                notifyDataSetChanged();
                                calculateTotal();
                                db.collection("Users").document(user.getuID())
                                        .update(
                                                "cart", user.getCart()
                                        );

                            }
                        });


                    } else {

                    }


                    ;
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemQuantity, total;
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


    private void calculateTotal() {
        total = (TextView) ((Activity) fragmentContext).findViewById(R.id.total);


        int sum = 0;
        for (int i = 0; i < itemArrayList.size(); i++) {
            sum = sum + (Integer.parseInt(itemArrayList.get(i).getItemPrice()) * quantity.get(i));
        }
        System.out.println("Sum " + sum);
        total.setText(sum + " JD");
    }

}
