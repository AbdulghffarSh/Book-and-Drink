package com.abdulghffar.drink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class cartFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    cartAdapter adapter;
    ArrayList<item> itemArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_cart, container, false);
        itemArrayList=new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        adapter = new cartAdapter(view.getContext(), itemArrayList);
        recyclerView.setAdapter(adapter);








        return view;

    }
}