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
    ArrayList<item> itemArrayList;
    Adapter adapter;


    ArrayList<String> itemIDArrayList;
    ArrayList<String> itemNameArrayList;
    ArrayList<String> itemPriceArrayList;
    ArrayList<String> itemDescriptionArrayList;
    ArrayList<String> itemPicURLArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        itemIDArrayList = new ArrayList<>();
        itemNameArrayList = new ArrayList<>();
        itemPriceArrayList = new ArrayList<>();
        itemDescriptionArrayList = new ArrayList<>();
        itemArrayList = new ArrayList<>();
        itemPicURLArrayList = new ArrayList<>();
        Bundle bundle = getArguments();

        addData(bundle);

        System.out.println(itemArrayList);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));


        adapter = new Adapter(view.getContext(), itemArrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void addData(Bundle bundle) {

        itemIDArrayList = bundle.getStringArrayList("itemIDArrayList");
        itemPicURLArrayList = bundle.getStringArrayList("itemPicURLArrayList");
        itemNameArrayList = bundle.getStringArrayList("itemNameArrayList");
        itemPriceArrayList = bundle.getStringArrayList("itemPriceArrayList");
        itemDescriptionArrayList = bundle.getStringArrayList("itemDescriptionArrayList");


        for (int i = 0; i < (itemIDArrayList.size() - 1); i++) {
            itemArrayList.add(new item(itemIDArrayList.get(i),
                    itemNameArrayList.get(i), itemPriceArrayList.get(i), itemDescriptionArrayList.get(i),itemPicURLArrayList.get(i)));
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



