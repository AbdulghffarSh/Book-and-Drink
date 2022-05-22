package com.abdulghffar.drink;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class homeFragment extends Fragment {

    View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private Adapter adapter;
    String itemPicURL;
    ArrayList<item> drinks= new ArrayList<>();


    float itemPrice;
    String itemName;
    String itemDescription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        //addData

        db.collection("Drinks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                itemName = (document.getData().get("drinkName")).toString();
                                itemPrice = Float.parseFloat((Objects.requireNonNull(document.getData().get("price")).toString()));
                                itemDescription = Objects.requireNonNull(document.getData().get("description")).toString();
                                itemPicURL = Objects.requireNonNull(document.getData().get("imgUrl")).toString();

                                drinks.add(new item(itemName, itemPrice, itemDescription, itemPicURL));                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }

                });


        System.out.println("drinks            "+drinks);


//        recyclerView = view.findViewById(R.id.recycler_view);
//
//        adapter = new Adapter(drinks);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(adapter);


        return view;
    }



}



