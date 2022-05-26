package com.abdulghffar.drink;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

    ArrayList<String> itemIDArrayList;
    ArrayList<String> itemNameArrayList;
    ArrayList<String> itemPriceArrayList;
    ArrayList<String> itemDescriptionArrayList;
    ArrayList<String> itemPicURLArrayList;
    ArrayList<item> cartArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cartArrayList = new ArrayList<>();


        itemIDArrayList = new ArrayList<>();
        itemNameArrayList = new ArrayList<>();
        itemPriceArrayList = new ArrayList<>();
        itemDescriptionArrayList = new ArrayList<>();
        itemArrayList = new ArrayList<>();
        itemPicURLArrayList = new ArrayList<>();



        Bundle bundle = getArguments();

        addData(bundle);


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



