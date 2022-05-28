package com.abdulghffar.drink;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class profileFragment extends Fragment {
    View view;
    TextView name;
    User user;
    FirebaseFirestore db;
    ImageView avatar;
    ImageButton whatsapp;
    ImageButton email;
    TextView info;
    TextView address;
    ImageButton editButton;

    EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.name);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        whatsapp = (ImageButton) view.findViewById(R.id.whatsappButton);
        email = (ImageButton) view.findViewById(R.id.emailButton);
        info = (TextView) view.findViewById(R.id.info);
        address = (TextView) view.findViewById(R.id.address);
        editText = (EditText) view.findViewById(R.id.edittext);
        editButton = (ImageButton) view.findViewById(R.id.editButton);
        editText.setVisibility(View.GONE);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getVisibility() == View.VISIBLE){
                    address.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                }
                else{
                    address.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);


                }
            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=962792869744&text=I%20am%20a%20user%20of%20Book%26Drink"));
                startActivity(browserIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"Abdulghffar.sh@hotmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Inquiry - a user from Book&Drink");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toasty.warning(view.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT, true).show();

                }
            }
        });


        getUserData();

        return view;
    }


    public void getUserData() {
        db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            name.setText(firebaseUser.getDisplayName());

            DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    //change image
                    if (user.getGender().equals("Male")) {
                        avatar.setImageResource(R.drawable.guy);
                    }
                    if (user.getGender().equals("Female")) {
                        avatar.setImageResource(R.drawable.girl);
                    }

                    info.setText(user.getFullName()+"\n"+user.getEmail()+"\n"+user.getPhoneNumber()+"\n"+user.getGender());
                    if (user.getAddress() != null) {
                        address.setText(user.getAddress().toString());
                    }
                    else{
                        address.setText("There is no address");
                    }
                }
            });




        } else {
            // No user is signed in
        }


    }


}