package com.abdulghffar.drink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileFragment extends Fragment {
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }


    public void getUserData(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            user

        } else {
            // No user is signed in
        }



    }

}