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

public class bookShopFragment extends Fragment
{

    View view;

    RecyclerView recyclerView;
    ArrayList < item > itemArrayList;
    Adapter adapter;

    ImageButton imageButton;

    ArrayList < String > booksIDArrayList;
    ArrayList < String > booksNameArrayList;
    ArrayList < String > booksDescriptionArrayList;
    ArrayList < String > booksPicURLArrayList;
    ArrayList < String > booksPriceArrayList;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {


        booksIDArrayList = new ArrayList <> ();
        booksPicURLArrayList = new ArrayList <> ();
        booksNameArrayList = new ArrayList <> ();
        booksPriceArrayList = new ArrayList <> ();
        itemArrayList = new ArrayList <> ();
        booksDescriptionArrayList = new ArrayList <> ();


        Bundle bundle = getArguments ();

        addData (bundle);


        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder ().permitAll ().build ();
        StrictMode.setThreadPolicy (policy);

        view = inflater.inflate (R.layout.fragment_bookshop, container, false);

        recyclerView = view.findViewById (R.id.recyclerview);
        recyclerView.setHasFixedSize (true);
        recyclerView.
                setLayoutManager (new GridLayoutManager (view.getContext (), 2));


        adapter = new Adapter (view.getContext (), itemArrayList);
        recyclerView.setAdapter (adapter);


        return view;
    }

    private void addData (Bundle bundle)
    {
        itemArrayList.clear ();
        booksIDArrayList = bundle.getStringArrayList ("booksIDArrayList");
        booksPicURLArrayList = bundle.getStringArrayList ("booksPicURLArrayList");
        booksNameArrayList = bundle.getStringArrayList ("booksNameArrayList");
        booksPriceArrayList = bundle.getStringArrayList ("booksPriceArrayList");
        booksDescriptionArrayList =
                bundle.getStringArrayList ("booksDescriptionArrayList");


        for (int i = 0; i < (booksIDArrayList.size () - 1); i++)
        {
            itemArrayList.add (new item (booksIDArrayList.get (i),
                    booksNameArrayList.get (i),
                    booksPriceArrayList.get (i),
                    booksDescriptionArrayList.get (i),
                    booksPicURLArrayList.get (i)));
        }




    }
    public static Drawable LoadImageFromWebOperations (String url)
    {
        try
        {
            InputStream is = (InputStream) new URL (url).getContent ();
            Drawable d = Drawable.createFromStream (is, "src name");
            return d;
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
