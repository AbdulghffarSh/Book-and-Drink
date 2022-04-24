package com.abdulghffar.drink;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class homeFragment extends Fragment {

    View view;
    ArrayList<String> imagesArraylist = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        view = inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("images");



        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot images: dataSnapshot.getChildren()){
                    String imageLink=images.child("link").getValue().toString();
                    imagesArraylist.add(imageLink);
                }
                System.out.println(imagesArraylist.get(0));
                try {

                    ImageView advImage = (ImageView)view.findViewById(R.id.advImage);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imagesArraylist.get(0)).getContent());
                    advImage.setImageBitmap(bitmap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}