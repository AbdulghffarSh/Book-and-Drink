package com.abdulghffar.drink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.ibrahimsn.lib.SmoothBottomBar;

public class mainActivity extends AppCompatActivity
{

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView header;
    SmoothBottomBar bottomBar;
    FirebaseFirestore db;
    ArrayList < item > drinksItemArrayList;
    ArrayList < item > booksItemArrayList;


    @Override protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.activity_main);
        setup ();
        bottomBar.setOnItemSelected ((Function1 < ? super Integer, Unit >) o->
                {
                    switch (o)
                    {
                        case 0 :
                            homeFragment ();
                            break;
                        case 1:bookShopFragment ();
                            break;
                        case 2:cartFragment ();
                            break;
                        case 3:profileFragment ();
                            break; default:homeFragment ();}
                    return null;}
        );

    }

    private void setup ()
    {
        firebaseAuth = FirebaseAuth.getInstance ();
        firebaseUser = firebaseAuth.getCurrentUser ();
        EventChangeListener ();
        header = (TextView) findViewById (R.id.header_title);
        bottomBar = (SmoothBottomBar) findViewById (R.id.bottomBar);


    }

    private void replaceFragment (Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager ();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.frameLayout, fragment);
        fragmentTransaction.commit ();


    }

    public void homeFragment ()
    {

        ArrayList < String > drinksIDArrayList = new ArrayList <> ();
        ArrayList < String > drinksPicURLArrayList = new ArrayList <> ();
        ArrayList < String > drinksNameArrayList = new ArrayList <> ();
        ArrayList < String > drinksPriceArrayList = new ArrayList <> ();
        ArrayList < String > drinksDescriptionArrayList = new ArrayList <> ();


        for (item item:drinksItemArrayList)
        {
            drinksIDArrayList.add (item.getItemID ());
            drinksPicURLArrayList.add (item.getItemPicURL ());
            drinksNameArrayList.add (item.getItemName ());
            drinksPriceArrayList.add (item.getItemPrice ());
            drinksDescriptionArrayList.add (item.getItemDescription ());


        }


        Bundle bundle = new Bundle ();
        bundle.putStringArrayList ("drinksIDArrayList", drinksIDArrayList);
        bundle.putStringArrayList ("drinksPicURLArrayList",
                drinksPicURLArrayList);
        bundle.putStringArrayList ("drinksNameArrayList", drinksNameArrayList);
        bundle.putStringArrayList ("drinksPriceArrayList", drinksPriceArrayList);
        bundle.putStringArrayList ("drinksDescriptionArrayList",
                drinksDescriptionArrayList);

        homeFragment homeFragment = new homeFragment ();
        homeFragment.setArguments (bundle);


        header.setText ("Home");
        replaceFragment (homeFragment);


    }

    public void bookShopFragment ()
    {

        ArrayList < String > booksIDArrayList = new ArrayList <> ();
        ArrayList < String > booksPicURLArrayList = new ArrayList <> ();
        ArrayList < String > booksNameArrayList = new ArrayList <> ();
        ArrayList < String > booksPriceArrayList = new ArrayList <> ();
        ArrayList < String > booksDescriptionArrayList = new ArrayList <> ();


        for (item item:booksItemArrayList)
        {
            booksIDArrayList.add (item.getItemID ());
            booksPicURLArrayList.add (item.getItemPicURL ());
            booksNameArrayList.add (item.getItemName ());
            booksPriceArrayList.add (item.getItemPrice ());
            booksDescriptionArrayList.add (item.getItemDescription ());


        }

        Bundle bundle = new Bundle ();
        bundle.putStringArrayList ("booksIDArrayList", booksIDArrayList);
        bundle.putStringArrayList ("booksPicURLArrayList", booksPicURLArrayList);
        bundle.putStringArrayList ("booksNameArrayList", booksNameArrayList);
        bundle.putStringArrayList ("booksPriceArrayList", booksPriceArrayList);
        bundle.putStringArrayList ("booksDescriptionArrayList",
                booksDescriptionArrayList);

        bookShopFragment bookShopFragment = new bookShopFragment ();
        bookShopFragment.setArguments (bundle);


        header.setText ("BookShop");
        replaceFragment (bookShopFragment);


    }


    public void profileFragment ()
    {
        header.setText ("Profile");
        replaceFragment (new profileFragment ());

    }


    private void EventChangeListener ()
    {
        db = FirebaseFirestore.getInstance ();
        drinksItemArrayList = new ArrayList <> ();
        booksItemArrayList = new ArrayList <> ();

        db.collection ("Drinks").orderBy ("itemName",
                Query.Direction.ASCENDING).
                addSnapshotListener (new EventListener < QuerySnapshot > ()
                                     {
                                         @Override
                                         public void onEvent (@Nullable QuerySnapshot value,
                                                              @Nullable
                                                                      FirebaseFirestoreException
                                                                      error)
                                         {

                                             if (error != null)
                                             {

                                                 Log.e ("Firesore error", error.getMessage ());
                                                 return;}

                                             for (DocumentChange dc:value.
                                                     getDocumentChanges
                                                             ())
                                             {

                                                 if (dc.getType () == DocumentChange.Type.ADDED)
                                                 {

                                                     drinksItemArrayList.add (dc.getDocument ().
                                                             toObject (item.class));
                                                     drinksItemArrayList.get (drinksItemArrayList.
                                                             size () -
                                                             1).setItemID (dc.
                                                             getDocument
                                                                     ().getId ().
                                                             toString
                                                                     ());}

                                             }

                                         }

                                     }
                );


        db.collection ("Books").orderBy ("itemName",
                Query.Direction.ASCENDING).
                addSnapshotListener (new EventListener < QuerySnapshot > ()
                                     {
                                         @Override
                                         public void onEvent (@Nullable QuerySnapshot value,
                                                              @Nullable
                                                                      FirebaseFirestoreException
                                                                      error)
                                         {

                                             if (error != null)
                                             {

                                                 Log.e ("Firesore error", error.getMessage ());
                                                 return;}

                                             for (DocumentChange dc:value.
                                                     getDocumentChanges
                                                             ())
                                             {

                                                 if (dc.getType () == DocumentChange.Type.ADDED)
                                                 {

                                                     booksItemArrayList.add (dc.getDocument ().
                                                             toObject (item.class));
                                                     booksItemArrayList.get (booksItemArrayList.
                                                             size () -
                                                             1).setItemID (dc.
                                                             getDocument
                                                                     ().getId ().
                                                             toString
                                                                     ());}

                                             }

                                         }

                                     }
                );


    }

    public void logout (View view)
    {
        Bundle bundle = new Bundle ();
        bundle.putString ("logout", "logout");
        startActivity (new Intent (this, signIn.class).putExtras (bundle));


    }

    public void cartFragment ()
    {
        header.setText ("Cart");
        replaceFragment (new cartFragment ());


    }
}
