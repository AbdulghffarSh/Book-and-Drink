package com.abdulghffar.drink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class cartFragment extends Fragment
{
    View view;
    RecyclerView recyclerView;
    cartAdapter adapter;
    ArrayList < item > itemArrayList;
    FirebaseFirestore db;
    User user;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {



        view = inflater.inflate (R.layout.fragment_cart, container, false);
        itemArrayList = new ArrayList <> ();
        recyclerView = view.findViewById (R.id.recyclerview);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager(view.getContext ()));


        getData();

        adapter = new cartAdapter (view.getContext (), itemArrayList);
        recyclerView.setAdapter (adapter);






        return view;

    }


    private void getData(){
        ArrayList<String> cartItemsArrayList = new ArrayList<>();
        ArrayList<Integer> cartItemsQuantityArrayList = new ArrayList<>();

        Bundle bundle = getArguments ();
        assert bundle != null;
        cartItemsArrayList = bundle.getStringArrayList ("cartItemsArrayList");
        cartItemsQuantityArrayList = bundle.getIntegerArrayList("cartItemsQuantityArrayList");

        HashMap<String,Integer> map = new HashMap<>();

        ArrayList<String> finalCartItemsArrayList = cartItemsArrayList;
        ArrayList<Integer> finalCartItemsQuantityArrayList = cartItemsQuantityArrayList;
        Map<String, Integer> days = IntStream.range(0, cartItemsArrayList.size()).boxed()
                .collect(Collectors.toMap(i -> finalCartItemsArrayList.get(i), i -> finalCartItemsQuantityArrayList.get(i)));

        System.out.println("Testttttt"+days);






    }


}
