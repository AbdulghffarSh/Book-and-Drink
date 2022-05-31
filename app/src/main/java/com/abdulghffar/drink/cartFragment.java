package com.abdulghffar.drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class cartFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    cartAdapter adapter;
    ArrayList<item> itemArrayList;
    FirebaseFirestore db;
    User user;
    Map<String, Integer> cart;

    ArrayList<String> drinksIDArrayList;
    ArrayList<String> drinksNameArrayList;
    ArrayList<String> drinksPriceArrayList;
    ArrayList<String> drinksDescriptionArrayList;
    ArrayList<String> drinksPicURLArrayList;

    ArrayList<String> booksIDArrayList;
    ArrayList<String> booksNameArrayList;
    ArrayList<String> booksDescriptionArrayList;
    ArrayList<String> booksPicURLArrayList;
    ArrayList<String> booksPriceArrayList;

    ArrayList<String> cartItemIds;
    ArrayList<Integer> cartItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        Bundle bundle = getArguments();
        ArrayList<item> itemArrayList = new ArrayList<>();
        itemArrayList.size();

        drinksIDArrayList = bundle.getStringArrayList("drinksIDArrayList");
        drinksPicURLArrayList = bundle.getStringArrayList("drinksPicURLArrayList");
        drinksNameArrayList = bundle.getStringArrayList("drinksNameArrayList");
        drinksPriceArrayList = bundle.getStringArrayList("drinksPriceArrayList");
        drinksDescriptionArrayList = bundle.getStringArrayList("drinksDescriptionArrayList");

        booksIDArrayList = bundle.getStringArrayList("booksIDArrayList");
        booksPicURLArrayList = bundle.getStringArrayList("booksPicURLArrayList");
        booksNameArrayList = bundle.getStringArrayList("booksNameArrayList");
        booksPriceArrayList = bundle.getStringArrayList("booksPriceArrayList");
        booksDescriptionArrayList = bundle.getStringArrayList("booksDescriptionArrayList");

        cartItemIds = bundle.getStringArrayList("cartItemIds");
        cartItemCount = bundle.getIntegerArrayList("cartItemCount");




        for (int i = 0; i < (drinksIDArrayList.size() - 1); i++) {
            itemArrayList.add(new item(drinksIDArrayList.get(i),
                    drinksNameArrayList.get(i),
                    drinksPriceArrayList.get(i),
                    drinksDescriptionArrayList.get(i),
                    drinksPicURLArrayList.get(i)));
        }


        for (int i = 0; i < (booksIDArrayList.size() - 1); i++) {
            itemArrayList.add(new item(booksIDArrayList.get(i),
                    booksNameArrayList.get(i),
                    booksPriceArrayList.get(i),
                    booksDescriptionArrayList.get(i),
                    booksPicURLArrayList.get(i)));
        }

        for(int i =0 ;i<(itemArrayList.size() - 1);i++){
            if(!cartItemIds.contains(itemArrayList.get(i).getItemID())){
                itemArrayList.remove(itemArrayList.get(i));
            }

        }
        System.out.println(itemArrayList);



        adapter = new cartAdapter(view.getContext(), itemArrayList);
        recyclerView.setAdapter(adapter);


        return view;

    }





}
