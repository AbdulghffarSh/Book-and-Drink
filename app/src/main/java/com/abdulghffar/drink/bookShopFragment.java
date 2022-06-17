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

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class bookShopFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    ArrayList<item> itemArrayList;
    Adapter adapter;

    ImageButton imageButton;

    ArrayList<String> booksIDArrayList;
    ArrayList<String> booksNameArrayList;
    ArrayList<String> booksDescriptionArrayList;
    ArrayList<String> booksPicURLArrayList;
    ArrayList<String> booksPriceArrayList;
    ArrayList<String> booksTagArrayList;
    ArrayList<item>   filteredDrinksItemArrayList;


    androidx.appcompat.widget.SearchView searchView;
    Button allFilter,novels,classics,language;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        booksIDArrayList = new ArrayList<>();
        booksPicURLArrayList = new ArrayList<>();
        booksNameArrayList = new ArrayList<>();
        booksPriceArrayList = new ArrayList<>();
        itemArrayList = new ArrayList<>();
        booksDescriptionArrayList = new ArrayList<>();
        booksTagArrayList = new ArrayList<>();
        filteredDrinksItemArrayList = new ArrayList<>();





        Bundle bundle = getArguments();

        addData(bundle);
        filteredDrinksItemArrayList = itemArrayList;



        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_bookshop, container, false);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        allFilter = view.findViewById(R.id.allFilter);
        novels = view.findViewById(R.id.novels);
        classics = view.findViewById(R.id.classics);
        language = view.findViewById(R.id.language);



        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.
                setLayoutManager(new GridLayoutManager(view.getContext(), 2));


        allFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(allFilter);

                addData(bundle);

                filteredDrinksItemArrayList = itemArrayList;
                adapter.notifyDataSetChanged();

            }
        });

        novels.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(novels);

                addData(bundle);

                filteredDrinksItemArrayList = itemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("Novels"));
                adapter.notifyDataSetChanged();

            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        classics.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(classics);

                addData(bundle);

                filteredDrinksItemArrayList = itemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("Classics"));
                adapter.notifyDataSetChanged();

            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                changeColor(language);

                addData(bundle);

                filteredDrinksItemArrayList = itemArrayList;
                filteredDrinksItemArrayList.removeIf(item -> !item.getItemTag().equals("Language"));
                adapter.notifyDataSetChanged();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

                return true;
            }
        });


        adapter = new Adapter(view.getContext(), itemArrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }



    private void addData(Bundle bundle) {
        itemArrayList.clear();
        booksIDArrayList = bundle.getStringArrayList("booksIDArrayList");
        booksPicURLArrayList = bundle.getStringArrayList("booksPicURLArrayList");
        booksNameArrayList = bundle.getStringArrayList("booksNameArrayList");
        booksPriceArrayList = bundle.getStringArrayList("booksPriceArrayList");
        booksDescriptionArrayList = bundle.getStringArrayList("booksDescriptionArrayList");
        booksTagArrayList = bundle.getStringArrayList("booksTagArrayList");


        for (int i = 0; i < (booksIDArrayList.size() - 1); i++) {
            itemArrayList.add(new item(booksIDArrayList.get(i),
                    booksNameArrayList.get(i),
                    booksPriceArrayList.get(i),
                    booksDescriptionArrayList.get(i),
                    booksPicURLArrayList.get(i),
                    booksTagArrayList.get(i)));
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


    private void changeColor(Button chosenButton) {

        allFilter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));
        novels.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));
        classics.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));
        language.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE1E5EB")));

        allFilter.setTextColor(Color.parseColor("#FF0A2658"));
        novels.setTextColor(Color.parseColor("#FF0A2658"));
        classics.setTextColor(Color.parseColor("#FF0A2658"));
        language.setTextColor(Color.parseColor("#FF0A2658"));

        chosenButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0A2658")));
        chosenButton.setTextColor(Color.parseColor("#FFE1E5EB"));


    }
    private void filterList(String text){
        changeColor(allFilter);

        ArrayList<item> filteredList=new ArrayList<>();
        for(item item:itemArrayList){
            if(item.getItemName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){

        }else{
            adapter.setFilteredList(filteredList);
        }
    }

}
