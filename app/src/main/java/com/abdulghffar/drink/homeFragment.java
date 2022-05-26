package com.abdulghffar.drink;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class homeFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    ArrayList<item> drinksItemArrayList;
    Adapter adapter;

    ImageButton imageButton;

    ArrayList<String> drinksIDArrayList;
    ArrayList<String> drinksNameArrayList;
    ArrayList<String> drinksPriceArrayList;
    ArrayList<String> drinksDescriptionArrayList;
    ArrayList<String> drinksPicURLArrayList;
    ArrayList<item> cartArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cartArrayList = new ArrayList<>();


        drinksIDArrayList = new ArrayList<>();
        drinksNameArrayList = new ArrayList<>();
        drinksPriceArrayList = new ArrayList<>();
        drinksDescriptionArrayList = new ArrayList<>();
        drinksItemArrayList = new ArrayList<>();
        drinksPicURLArrayList = new ArrayList<>();

//        imageButton = (imageButton).findViewById(R.id.add);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Bundle bundle = getArguments();

        addData(bundle);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));


        adapter = new Adapter(view.getContext(), drinksItemArrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void addData(Bundle bundle) {

        drinksIDArrayList = bundle.getStringArrayList("drinksIDArrayList");
        drinksPicURLArrayList = bundle.getStringArrayList("drinksPicURLArrayList");
        drinksNameArrayList = bundle.getStringArrayList("drinksNameArrayList");
        drinksPriceArrayList = bundle.getStringArrayList("drinksPriceArrayList");
        drinksDescriptionArrayList = bundle.getStringArrayList("drinksDescriptionArrayList");


        for (int i = 0; i < (drinksIDArrayList.size() - 1); i++) {
            drinksItemArrayList.add(new item(drinksIDArrayList.get(i),
                    drinksNameArrayList.get(i), drinksPriceArrayList.get(i), drinksDescriptionArrayList.get(i),drinksPicURLArrayList.get(i)));
        }




    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}



