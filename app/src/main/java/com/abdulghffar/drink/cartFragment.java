package com.abdulghffar.drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    TextView total;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        Bundle bundle = getArguments();
        ArrayList<item> itemArrayList = new ArrayList<>();

        itemsIDArrayList = bundle.getStringArrayList("itemsIDArrayList");
        itemsPicURLArrayList = bundle.getStringArrayList("itemsPicURLArrayList");
        itemsNameArrayList = bundle.getStringArrayList("itemsNameArrayList");
        itemsPriceArrayList = bundle.getStringArrayList("itemsPriceArrayList");
        itemsDescriptionArrayList = bundle.getStringArrayList("itemsDescriptionArrayList");
        cartItemCount = bundle.getIntegerArrayList("cartItemCount");


        for (int i = 0; i < itemsIDArrayList.size(); i++) {
            itemArrayList.add(new item(itemsIDArrayList.get(i), itemsNameArrayList.get(i), itemsPriceArrayList.get(i), itemsDescriptionArrayList.get(i), itemsPicURLArrayList.get(i)));
        }

        int totalPrice = 0;
        for (int i = 0; i < itemArrayList.size(); i++) {
            totalPrice = totalPrice + (Integer.parseInt(itemArrayList.get(i).getItemPrice()) * cartItemCount.get(i));
        }
        System.out.println(totalPrice);
        total = (TextView) view.findViewById(R.id.total);
        total.setText(totalPrice + " JD");


        adapter = new cartAdapter(view.getContext(), itemArrayList, cartItemCount);
        recyclerView.setAdapter(adapter);


        return view;

    }


}
