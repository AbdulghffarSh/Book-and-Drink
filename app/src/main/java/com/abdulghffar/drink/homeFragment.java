package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class homeFragment extends Fragment {

    View view;


    RecyclerView recyclerView;
    ArrayList<item> allDrinksItemArrayList;
    ArrayList<item> filteredDrinksItemArrayList;


    Adapter adapter;

    ImageButton imageButton;
    Button cappuccinoFilter, allFilter, americanoFilter, espressoFilter;

    ArrayList<String> drinksIDArrayList;
    ArrayList<String> drinksNameArrayList;
    ArrayList<String> drinksPriceArrayList;
    ArrayList<String> drinksDescriptionArrayList;
    ArrayList<String> drinksPicURLArrayList;
    ArrayList<String> drinksTagArrayList;

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        drinksIDArrayList = new ArrayList<>();
        drinksNameArrayList = new ArrayList<>();
        drinksPriceArrayList = new ArrayList<>();
        drinksDescriptionArrayList = new ArrayList<>();
        allDrinksItemArrayList = new ArrayList<>();
        drinksPicURLArrayList = new ArrayList<>();
        drinksTagArrayList = new ArrayList<>();
        filteredDrinksItemArrayList = new ArrayList<>();


        Bundle bundle = getArguments();

        addData(bundle);
        filteredDrinksItemArrayList = allDrinksItemArrayList;

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        //setup buttons
        cappuccinoFilter = (Button) view.findViewById(R.id.cappuccinoFilter);
        allFilter = (Button) view.findViewById(R.id.allFilter);
        americanoFilter = (Button) view.findViewById(R.id.americanoFilter);
        espressoFilter = (Button) view.findViewById(R.id.espressoFilter);


        cappuccinoFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(cappuccinoFilter);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("cappuccino"));
                adapter.notifyDataSetChanged();

                System.out.println("allDrinksItemArrayList:   " + allDrinksItemArrayList.size());
                System.out.println("filteredDrinksItemArrayList:   " + filteredDrinksItemArrayList.size());


            }
        });

        americanoFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(americanoFilter);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("americano"));
                adapter.notifyDataSetChanged();

                System.out.println("allDrinksItemArrayList:   " + allDrinksItemArrayList.size());
                System.out.println("filteredDrinksItemArrayList:   " + filteredDrinksItemArrayList.size());
            }
        });

        espressoFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(espressoFilter);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("espresso"));
                adapter.notifyDataSetChanged();

                System.out.println("allDrinksItemArrayList:   " + allDrinksItemArrayList.size());
                System.out.println("filteredDrinksItemArrayList:   " + filteredDrinksItemArrayList.size());
            }
        });

        allFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(allFilter);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                adapter.notifyDataSetChanged();

                System.out.println("allDrinksItemArrayList:   " + allDrinksItemArrayList.size());
                System.out.println("filteredDrinksItemArrayList:   " + filteredDrinksItemArrayList.size());
            }
        });


        adapter = new Adapter(view.getContext(), filteredDrinksItemArrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void addData(Bundle bundle) {
        allDrinksItemArrayList.clear();

        drinksIDArrayList = bundle.getStringArrayList("drinksIDArrayList");
        drinksPicURLArrayList = bundle.getStringArrayList("drinksPicURLArrayList");
        drinksNameArrayList = bundle.getStringArrayList("drinksNameArrayList");
        drinksPriceArrayList = bundle.getStringArrayList("drinksPriceArrayList");
        drinksDescriptionArrayList = bundle.getStringArrayList("drinksDescriptionArrayList");
        drinksTagArrayList = bundle.getStringArrayList("booksTagArrayList");


        for (int i = 0; i < (drinksIDArrayList.size() - 1); i++) {
            allDrinksItemArrayList.add(new item(drinksIDArrayList.get(i),
                    drinksNameArrayList.get(i),
                    drinksPriceArrayList.get(i),
                    drinksDescriptionArrayList.get(i),
                    drinksPicURLArrayList.get(i),
                    drinksTagArrayList.get(i)));
        }


    }

    private void changeColor(Button chosenButton) {

        allFilter.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")) );
        americanoFilter.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")) );
        espressoFilter.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")) );
        cappuccinoFilter.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")) );


        allFilter.setTextColor(Color.parseColor("#FF0A2658"));
        americanoFilter.setTextColor(Color.parseColor("#FF0A2658"));
        espressoFilter.setTextColor(Color.parseColor("#FF0A2658"));
        cappuccinoFilter.setTextColor(Color.parseColor("#FF0A2658"));

        chosenButton.setBackgroundTintList( ColorStateList.valueOf(Color.parseColor("#FF0A2658")) );
        chosenButton.setTextColor(Color.parseColor("#FFE1E5EB"));


    }


}
