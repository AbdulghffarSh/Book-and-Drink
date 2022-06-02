package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class cartFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    cartAdapter adapter;

    ArrayList<String> itemsIDArrayList;
    ArrayList<String> itemsNameArrayList;
    ArrayList<String> itemsPriceArrayList;
    ArrayList<String> itemsDescriptionArrayList;
    ArrayList<String> itemsPicURLArrayList;
    ArrayList<Integer> cartItemCount;
    FirebaseFirestore db;
    User user;
    ArrayList<item> itemArrayList;

    List<Integer> quantity;

    TextView total;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        //getCartData
        //getDrinks
        itemArrayList = new ArrayList<>();


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
                    System.out.println("Test   " + user.getCart());


                    db.collection("Drinks").orderBy("itemName",
                            Query.Direction.ASCENDING).
                            addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot value,
                                                                        @Nullable
                                                                                FirebaseFirestoreException
                                                                                error) {

                                                        if (error != null) {

                                                            Log.e("Firesore error", error.getMessage());
                                                            return;
                                                        }

                                                        for (DocumentChange dc : value.
                                                                getDocumentChanges
                                                                        ()) {

                                                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                                                itemArrayList.add(dc.getDocument().
                                                                        toObject(item.class));
                                                                itemArrayList.get(itemArrayList.
                                                                        size() -
                                                                        1).setItemID(dc.
                                                                        getDocument
                                                                                ().getId().
                                                                        toString
                                                                                ());
                                                            }

                                                        }


                                                        //getBooks
                                                        db.collection("Books").orderBy("itemName",
                                                                Query.Direction.ASCENDING).
                                                                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                        @Override
                                                                                        public void onEvent(@Nullable QuerySnapshot value,
                                                                                                            @Nullable
                                                                                                                    FirebaseFirestoreException
                                                                                                                    error) {

                                                                                            if (error != null) {

                                                                                                Log.e("Firesore error", error.getMessage());
                                                                                                return;
                                                                                            }

                                                                                            for (DocumentChange dc : value.
                                                                                                    getDocumentChanges
                                                                                                            ()) {

                                                                                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                                                                                    itemArrayList.add(dc.getDocument().
                                                                                                            toObject(item.class));
                                                                                                    itemArrayList.get(itemArrayList.
                                                                                                            size() -
                                                                                                            1).setItemID(dc.
                                                                                                            getDocument
                                                                                                                    ().getId().
                                                                                                            toString
                                                                                                                    ());
                                                                                                }

                                                                                            }


                                                                                            System.out.println("Test size1  " + itemArrayList.size());

                                                                                            ArrayList<item> newItemArrayList = new ArrayList<>();
                                                                                            for (item item : itemArrayList) {

                                                                                                if (user.getCart().containsKey(item.getItemID())) {
                                                                                                    newItemArrayList.add(item);
                                                                                                }

                                                                                            }
                                                                                            System.out.println("Test size2  " + newItemArrayList.size());
                                                                                            System.out.println("Test " + newItemArrayList.get(0).getItemID());


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
                                                                                                        int sum = 0;
                                                                                                        ArrayList<Integer> quantity = new ArrayList(user.getCart().values());
                                                                                                        System.out.println("itemsSize " + itemArrayList.size());
                                                                                                        System.out.println("quantity " + quantity);
                                                                                                        total = view.findViewById(R.id.total);

                                                                                                        for (int i = 0; i < newItemArrayList.size(); i++) {
                                                                                                            sum = sum + (Integer.parseInt(newItemArrayList.get(i).getItemPrice()) * quantity.get(i));
                                                                                                        }
                                                                                                        System.out.println("Sum " + sum);
                                                                                                        total.setText(Integer.toString(sum) + " JD");


                                                                                                    }
                                                                                                });


                                                                                            } else {

                                                                                            }

                                                                                            adapter = new cartAdapter(view.getContext(), newItemArrayList, user.getCart());
                                                                                            recyclerView.setAdapter(adapter);


                                                                                        }

                                                                                    }
                                                                );


                                                    }

                                                }
                            );


                }
            });


        }


        return view;

    }
}
