package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.ibrahimsn.lib.SmoothBottomBar;

public class mainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView header;
    ImageButton refresh;
    SmoothBottomBar bottomBar;
    FirebaseFirestore db;
    ArrayList<item> drinksItemArrayList;
    ArrayList<item> booksItemArrayList;
    User user;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setup();


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });


        bottomBar.setOnItemSelected((Function1<? super Integer, Unit>) o ->
                {


                    switch (o) {
                        case 0:
                            homeFragment();
                            break;
                        case 1:
                            bookShopFragment();
                            break;
                        case 2:
                            cartFragment();
                            break;
                        case 3:
                            profileFragment();
                            break;
                        default:
                            homeFragment();
                            break;

                    }
                    return null;
                }
        );


        homeFragment();


    }


    private void setup() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        EventChangeListener();
        header = (TextView) findViewById(R.id.header_title);
        bottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);
        refresh = (ImageButton) findViewById(R.id.refresh);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }

    public void homeFragment() {
progressBar.setVisibility(View.VISIBLE);
        db = FirebaseFirestore.getInstance();
        drinksItemArrayList = new ArrayList<>();

        db.collection("Drinks").orderBy("itemName",
                Query.Direction.ASCENDING).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value,
                                                            @Nullable
                                                                    FirebaseFirestoreException
                                                                    error) {

                                            if (error != null) {

                                                Log.e("Firesore error", error.getMessage());
                                                return;
                                            }

                                            for (DocumentChange dc : value.
                                                    getDocumentChanges
                                                            ()) {

                                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                                    drinksItemArrayList.add(dc.getDocument().
                                                            toObject(item.class));
                                                    drinksItemArrayList.get(drinksItemArrayList.
                                                            size() -
                                                            1).setItemID(dc.
                                                            getDocument
                                                                    ().getId().
                                                            toString
                                                                    ());
                                                    ArrayList<String> drinksIDArrayList = new ArrayList<>();
                                                    ArrayList<String> drinksPicURLArrayList = new ArrayList<>();
                                                    ArrayList<String> drinksNameArrayList = new ArrayList<>();
                                                    ArrayList<String> drinksPriceArrayList = new ArrayList<>();
                                                    ArrayList<String> drinksDescriptionArrayList = new ArrayList<>();
                                                    ArrayList<String> drinksTagArrayList = new ArrayList<>();


                                                    for (item item : drinksItemArrayList) {
                                                        drinksIDArrayList.add(item.getItemID());
                                                        drinksPicURLArrayList.add(item.getItemPicURL());
                                                        drinksNameArrayList.add(item.getItemName());
                                                        drinksPriceArrayList.add(item.getItemPrice());
                                                        drinksDescriptionArrayList.add(item.getItemDescription());
                                                        drinksTagArrayList.add(item.getItemTag());

                                                    }


                                                    Bundle bundle = new Bundle();
                                                    bundle.putStringArrayList("drinksIDArrayList", drinksIDArrayList);
                                                    bundle.putStringArrayList("drinksPicURLArrayList", drinksPicURLArrayList);
                                                    bundle.putStringArrayList("drinksNameArrayList", drinksNameArrayList);
                                                    bundle.putStringArrayList("drinksPriceArrayList", drinksPriceArrayList);
                                                    bundle.putStringArrayList("drinksDescriptionArrayList", drinksDescriptionArrayList);
                                                    bundle.putStringArrayList("booksTagArrayList", drinksTagArrayList);


                                                    homeFragment homeFragment = new homeFragment();
                                                    homeFragment.setArguments(bundle);

                                                    header.setText("Home");
                                                    replaceFragment(homeFragment);


                                                }

                                            }

                                        }

                                    }
                );

        Timer timer = new Timer ();
        timer.schedule (new TimerTask()
        {
            public void run ()
            {

                progressBar.setVisibility(View.INVISIBLE);}
        }, 3000);


    }

    public void bookShopFragment() {

progressBar.setVisibility(View.VISIBLE);
        ArrayList<String> booksIDArrayList = new ArrayList<>();
        ArrayList<String> booksPicURLArrayList = new ArrayList<>();
        ArrayList<String> booksNameArrayList = new ArrayList<>();
        ArrayList<String> booksPriceArrayList = new ArrayList<>();
        ArrayList<String> booksDescriptionArrayList = new ArrayList<>();
        ArrayList<String> booksTagArrayList = new ArrayList<>();


        for (item item : booksItemArrayList) {
            booksIDArrayList.add(item.getItemID());
            booksPicURLArrayList.add(item.getItemPicURL());
            booksNameArrayList.add(item.getItemName());
            booksPriceArrayList.add(item.getItemPrice());
            booksDescriptionArrayList.add(item.getItemDescription());
            booksTagArrayList.add(item.getItemTag());


        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("booksIDArrayList", booksIDArrayList);
        bundle.putStringArrayList("booksPicURLArrayList", booksPicURLArrayList);
        bundle.putStringArrayList("booksNameArrayList", booksNameArrayList);
        bundle.putStringArrayList("booksPriceArrayList", booksPriceArrayList);
        bundle.putStringArrayList("booksDescriptionArrayList", booksDescriptionArrayList);
        bundle.putStringArrayList("booksTagArrayList", booksTagArrayList);


        bookShopFragment bookShopFragment = new bookShopFragment();
        bookShopFragment.setArguments(bundle);


        header.setText("BookShop");
        replaceFragment(bookShopFragment);

        Timer timer = new Timer ();
        timer.schedule (new TimerTask()
        {
            public void run ()
            {

                progressBar.setVisibility(View.INVISIBLE);}
        }, 3000);
    }


    public void profileFragment() {
        progressBar.setVisibility(View.VISIBLE);
        header.setText("Profile");
        replaceFragment(new profileFragment());
        Timer timer = new Timer ();
        timer.schedule (new TimerTask()
        {
            public void run ()
            {

                progressBar.setVisibility(View.INVISIBLE);}
        }, 1000);
    }


    private void EventChangeListener() {

        db = FirebaseFirestore.getInstance();
        drinksItemArrayList = new ArrayList<>();
        booksItemArrayList = new ArrayList<>();

        db.collection("Drinks").orderBy("itemName",
                Query.Direction.ASCENDING).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value,
                                                            @Nullable
                                                                    FirebaseFirestoreException
                                                                    error) {

                                            if (error != null) {

                                                Log.e("Firesore error", error.getMessage());
                                                return;
                                            }

                                            for (DocumentChange dc : value.
                                                    getDocumentChanges
                                                            ()) {

                                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                                    drinksItemArrayList.add(dc.getDocument().
                                                            toObject(item.class));
                                                    drinksItemArrayList.get(drinksItemArrayList.
                                                            size() -
                                                            1).setItemID(dc.
                                                            getDocument
                                                                    ().getId().
                                                            toString
                                                                    ());
                                                }

                                            }

                                        }

                                    }
                );


        db.collection("Books").orderBy("itemName",
                Query.Direction.ASCENDING).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value,
                                                            @Nullable
                                                                    FirebaseFirestoreException
                                                                    error) {

                                            if (error != null) {

                                                Log.e("Firesore error", error.getMessage());
                                                return;
                                            }

                                            for (DocumentChange dc : value.
                                                    getDocumentChanges
                                                            ()) {

                                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                                    booksItemArrayList.add(dc.getDocument().
                                                            toObject(item.class));
                                                    booksItemArrayList.get(booksItemArrayList.
                                                            size() -
                                                            1).setItemID(dc.
                                                            getDocument
                                                                    ().getId().
                                                            toString
                                                                    ());
                                                }

                                            }

                                        }

                                    }
                );


        db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            DocumentReference docRef =
                    db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<
                    DocumentSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void
                onSuccess(DocumentSnapshot
                                  documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                }


            });


        }


    }

    public void logout(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("logout", "logout");
        startActivity(new Intent(this, signIn.class).putExtras(bundle));


    }

    public void cartFragment() {

        try {
            db = FirebaseFirestore.getInstance();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {

                DocumentReference docRef =
                        db.collection("Users").document(firebaseUser.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<
                        DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void
                    onSuccess(DocumentSnapshot
                                      documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                    }


                });


            }
            progressBar.setVisibility(View.VISIBLE);

            header.setText("Cart");

            ArrayList<String> cartItemIds = new ArrayList<>(user.getCart().keySet());
            ArrayList<Integer> cartItemCount = new ArrayList<>(user.getCart().values());


            ArrayList<String> itemsIDArrayList = new ArrayList<>();
            ArrayList<String> itemsPicURLArrayList = new ArrayList<>();
            ArrayList<String> itemsNameArrayList = new ArrayList<>();
            ArrayList<String> itemsPriceArrayList = new ArrayList<>();
            ArrayList<String> itemsDescriptionArrayList = new ArrayList<>();
            ArrayList<String> itemsTagArrayList = new ArrayList<>();


            ArrayList<item> itemArrayList1 = new ArrayList<>();
            ArrayList<item> itemArrayList = new ArrayList<>();
            itemArrayList1.addAll(drinksItemArrayList);
            itemArrayList1.addAll(booksItemArrayList);




            for (int i = 0; i < (itemArrayList1.size()); i++) {
                if (cartItemIds.contains(itemArrayList1.get(i).getItemID())) {
                    itemArrayList.add(itemArrayList1.get(i));

                }

            }



            for (item item : itemArrayList) {
                itemsIDArrayList.add(item.getItemID());
                itemsPicURLArrayList.add(item.getItemPicURL());
                itemsNameArrayList.add(item.getItemName());
                itemsPriceArrayList.add(item.getItemPrice());
                itemsDescriptionArrayList.add(item.getItemDescription());
                itemsTagArrayList.add(item.getItemTag());
            }



            Bundle bundle = new Bundle();


            bundle.putStringArrayList("itemsDescriptionArrayList", itemsDescriptionArrayList);
            bundle.putStringArrayList("itemsIDArrayList", itemsIDArrayList);
            bundle.putStringArrayList("itemsPicURLArrayList", itemsPicURLArrayList);
            bundle.putStringArrayList("itemsNameArrayList", itemsNameArrayList);
            bundle.putStringArrayList("itemsPriceArrayList", itemsPriceArrayList);
            bundle.putStringArrayList("itemsTagArrayList", itemsTagArrayList);
            bundle.putIntegerArrayList("cartItemCount", cartItemCount);


            cartFragment cartFragment = new cartFragment();
            cartFragment.setArguments(bundle);
            replaceFragment(cartFragment);
            Timer timer = new Timer ();
            timer.schedule (new TimerTask()
            {
                public void run ()
                {

                    progressBar.setVisibility(View.INVISIBLE);}
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.info (mainActivity.this,
                    "The cart is empty",
                    Toast.LENGTH_SHORT,
                    true).show ();

        }


    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}
