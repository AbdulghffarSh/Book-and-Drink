package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class homeFragment extends Fragment {

    View view;


    RecyclerView recyclerView;
    ArrayList<item> allDrinksItemArrayList;
    ArrayList<item> filteredDrinksItemArrayList;


    Adapter adapter;

    ImageButton imageButton;
    Button hotDrinks, allFilter, coldDrinks, next, prev;
    ViewFlipper viewFlipper;

    ArrayList<String> drinksIDArrayList;
    ArrayList<String> drinksNameArrayList;
    ArrayList<String> drinksPriceArrayList;
    ArrayList<String> drinksDescriptionArrayList;
    ArrayList<String> drinksPicURLArrayList;
    ArrayList<String> drinksTagArrayList;


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;


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
        hotDrinks = (Button) view.findViewById(R.id.hotDrinks);
        allFilter = (Button) view.findViewById(R.id.allFilter);
        coldDrinks = (Button) view.findViewById(R.id.coldDrinks);
        next = (Button) view.findViewById(R.id.next);
        prev = (Button) view.findViewById(R.id.prev);
        viewFlipper = view.findViewById(R.id.viewFlipper);


        //flipperButtons
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
                viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
                viewFlipper.showNext();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(getContext(), R.anim.slide_in_right);
                viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);
                viewFlipper.showPrevious();
            }
        });




        coldDrinks.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(coldDrinks);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("cold"));
                adapter.notifyDataSetChanged();

                System.out.println("allDrinksItemArrayList:   " + allDrinksItemArrayList.size());
                System.out.println("filteredDrinksItemArrayList:   " + filteredDrinksItemArrayList.size());
            }
        });

        hotDrinks.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(hotDrinks);

                addData(bundle);

                filteredDrinksItemArrayList = allDrinksItemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("hot"));
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

        allFilter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));
        coldDrinks.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));
        hotDrinks.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));


        allFilter.setTextColor(Color.parseColor("#FF0A2658"));
        coldDrinks.setTextColor(Color.parseColor("#FF0A2658"));
        hotDrinks.setTextColor(Color.parseColor("#FF0A2658"));

        chosenButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0A2658")));
        chosenButton.setTextColor(Color.parseColor("#FFE1E5EB"));


    }

    @Override
    public void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                try {
                    handler.postDelayed(runnable, delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    viewFlipper.showNext();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, delay);
        super.onResume();
    }


}
