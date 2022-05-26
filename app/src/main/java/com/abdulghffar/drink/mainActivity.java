package com.abdulghffar.drink;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class mainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView header;
    SmoothBottomBar bottomBar;
    FirebaseFirestore db;
    ArrayList<item> drinksItemArrayList;
    ArrayList<item> booksItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setup();
        bottomBar.setOnItemSelected((Function1<? super Integer, Unit>) o -> {
            switch (o) {
                case 0:
                    homeFragment();
                    break;
                case 1:
                    bookShopFragment();
                    break;
                case 2:
                    profileFragment();
                    break;
                case 3:
                    profileFragment();
                    break;
                default:
                    homeFragment();
            }
            return null;
        });

    }

    private void setup() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        EventChangeListener();
        header = (TextView) findViewById(R.id.header_title);
        bottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }

    public void homeFragment() {

        ArrayList<String> itemIDArrayList = new ArrayList<>();
        ArrayList<String> itemPicURLArrayList = new ArrayList<>();
        ArrayList<String> itemNameArrayList = new ArrayList<>();
        ArrayList<String> itemPriceArrayList = new ArrayList<>();
        ArrayList<String> itemDescriptionArrayList = new ArrayList<>();


        for (item item : drinksItemArrayList) {
            itemIDArrayList.add(item.getItemID());
            itemPicURLArrayList.add(item.getItemPicURL());
            itemNameArrayList.add(item.getItemName());
            itemPriceArrayList.add(item.getItemPrice());
            itemDescriptionArrayList.add(item.getItemDescription());


        }


        Bundle bundle = new Bundle();
        bundle.putStringArrayList("itemIDArrayList", itemIDArrayList);
        bundle.putStringArrayList("itemPicURLArrayList", itemPicURLArrayList);
        bundle.putStringArrayList("itemNameArrayList", itemNameArrayList);
        bundle.putStringArrayList("itemPriceArrayList", itemPriceArrayList);
        bundle.putStringArrayList("itemDescriptionArrayList", itemDescriptionArrayList);

        homeFragment homeFragment = new homeFragment();
        homeFragment.setArguments(bundle);


        header.setText("Home");
        replaceFragment(homeFragment);


    }

    public void bookShopFragment() {

        ArrayList<String> itemIDArrayList = new ArrayList<>();
        ArrayList<String> itemPicURLArrayList = new ArrayList<>();
        ArrayList<String> itemNameArrayList = new ArrayList<>();
        ArrayList<String> itemPriceArrayList = new ArrayList<>();
        ArrayList<String> itemDescriptionArrayList = new ArrayList<>();


        for (item item : drinksItemArrayList) {
            itemIDArrayList.add(item.getItemID());
            itemPicURLArrayList.add(item.getItemPicURL());
            itemNameArrayList.add(item.getItemName());
            itemPriceArrayList.add(item.getItemPrice());
            itemDescriptionArrayList.add(item.getItemDescription());


        }


        Bundle bundle = new Bundle();
        bundle.putStringArrayList("itemIDArrayList", itemIDArrayList);
        bundle.putStringArrayList("itemPicURLArrayList", itemPicURLArrayList);
        bundle.putStringArrayList("itemNameArrayList", itemNameArrayList);
        bundle.putStringArrayList("itemPriceArrayList", itemPriceArrayList);
        bundle.putStringArrayList("itemDescriptionArrayList", itemDescriptionArrayList);

        bookShopFragment bookShopFragment = new bookShopFragment();
        bookShopFragment.setArguments(bundle);


        header.setText("BookShop");
        replaceFragment(bookShopFragment);


    }


    public void profileFragment() {
        header.setText("Profile");
        replaceFragment(new profileFragment());

    }


    private void EventChangeListener() {
        db = FirebaseFirestore.getInstance();
        drinksItemArrayList = new ArrayList<>();
        booksItemArrayList = new ArrayList<>();

        db.collection("Drinks").orderBy("itemName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            Log.e("Firesore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                drinksItemArrayList.add(dc.getDocument().toObject(item.class));
                                drinksItemArrayList.get(drinksItemArrayList.size() - 1).setItemID(dc.getDocument().getId().toString());
                            }

                        }

                    }

                });

        db.collection("Books").orderBy("itemName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            Log.e("Firesore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                booksItemArrayList.add(dc.getDocument().toObject(item.class));
                                booksItemArrayList.get(booksItemArrayList.size() - 1).setItemID(dc.getDocument().getId().toString());
                            }

                        }

                    }

                });

    }
}


