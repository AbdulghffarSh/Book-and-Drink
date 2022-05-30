package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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

    EditText city;
    EditText street;
    EditText building;
    EditText apartment;
    EditText phoneNumber;

    Button saveButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        setup();


        editButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (address.getVisibility() ==
                                                      View.VISIBLE) {
                                                  address.setVisibility(View.GONE);
                                                  city.setVisibility(View.VISIBLE);
                                                  street.setVisibility(View.VISIBLE);
                                                  building.setVisibility(View.VISIBLE);
                                                  apartment.setVisibility(View.VISIBLE);
                                                  phoneNumber.setVisibility(View.VISIBLE);
                                                  saveButton.setVisibility(View.VISIBLE);
                                              } else {
                                                  address.setVisibility(View.VISIBLE);
                                                  city.setVisibility(View.GONE);
                                                  street.setVisibility(View.GONE);
                                                  building.setVisibility(View.GONE);
                                                  apartment.setVisibility(View.GONE);
                                                  phoneNumber.setVisibility(View.GONE);
                                                  saveButton.setVisibility(View.GONE);
                                              }
                                          }
                                      }
        );


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse
                                        ("https://api.whatsapp.com/send?phone=962792869744&text=I%20am%20a%20user%20of%20Book%26Drink"));
                startActivity(browserIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {
                                "Abdulghffar.sh@hotmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,
                        "Inquiry - a user from Book&Drink");
                try {
                    startActivity(Intent.createChooser(i,
                            "Send mail..."));
                } catch (android.
                        content.ActivityNotFoundException ex) {
                    Toasty.warning(view.getContext(),
                            "There are no email clients installed.",
                            Toast.LENGTH_SHORT,
                            true).show();
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserData();
            }
        });

        return view;
    }


    public void getUserData() {
        db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            name.setText(firebaseUser.getDisplayName());

            DocumentReference docRef =
                    db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<
                                                      DocumentSnapshot>() {
                                                  @SuppressLint("SetTextI18n")
                                                  @Override
                                                  public void
                                                  onSuccess(DocumentSnapshot
                                                                    documentSnapshot) {
                                                      user =
                                                              documentSnapshot.
                                                                      toObject(User.class);
                                                      //change image
                                                      if (user.
                                                              getGender().equals("Male")) {
                                                          avatar.
                                                                  setImageResource
                                                                          (R.drawable.guy);
                                                      }
                                                      if (user.
                                                              getGender().equals
                                                              ("Female")) {
                                                          avatar.
                                                                  setImageResource
                                                                          (R.drawable.girl);
                                                      }

                                                      info.
                                                              setText(user.getFullName() +
                                                                      "\n" + user.getEmail() +
                                                                      "\n" +
                                                                      user.getPhoneNumber() +
                                                                      "\n" +
                                                                      user.getGender());
                                                      if (user.getAddress() != null) {
                                                          address.setText("City: " +
                                                                  user.getAddress
                                                                          ().get("city") +
                                                                  "\nStreet: " +
                                                                  user.getAddress
                                                                          ().
                                                                          get("street") +
                                                                  "\nBuilding: " +
                                                                  user.getAddress
                                                                          ().
                                                                          get("building")
                                                                  +
                                                                  "\nApartment: " +
                                                                  user.getAddress
                                                                          ().
                                                                          get
                                                                                  ("apartment"));
                                                          city.setText(user.
                                                                  getAddress().get
                                                                  ("city"));
                                                          street.
                                                                  setText(user.getAddress().get
                                                                          ("street"));
                                                          building.
                                                                  setText(user.getAddress().get
                                                                          ("building"));
                                                          apartment.
                                                                  setText(user.getAddress().get
                                                                          ("apartment"));
                                                          phoneNumber.
                                                                  setText(user.getPhoneNumber());
                                                      } else {
                                                          address.setText
                                                                  ("There is no address");
                                                      }


                                                  }
                                              }
            );


        } else {
            // No user is signed in
        }


    }

    public void setup() {

        name = (TextView) view.findViewById(R.id.name);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        whatsapp = (ImageButton) view.findViewById(R.id.whatsappButton);
        email = (ImageButton) view.findViewById(R.id.emailButton);
        info = (TextView) view.findViewById(R.id.info);
        address = (TextView) view.findViewById(R.id.address);


        city = (EditText) view.findViewById(R.id.city);
        street = (EditText) view.findViewById(R.id.street);
        building = (EditText) view.findViewById(R.id.building);
        apartment = (EditText) view.findViewById(R.id.apartment);
        phoneNumber = (EditText) view.findViewById(R.id.newPhoneNumber);
        saveButton = (Button) view.findViewById(R.id.saveNewData);

        editButton = (ImageButton) view.findViewById(R.id.editButton);

        city.setVisibility(View.GONE);
        street.setVisibility(View.GONE);
        building.setVisibility(View.GONE);
        apartment.setVisibility(View.GONE);
        phoneNumber.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        getUserData();


    }

    private void changeUserData() {

        if (city.getText().toString().isEmpty()) {
            Toasty.warning(view.getContext(), "Please fill the city field",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (street.getText().toString().isEmpty()) {
            Toasty.warning(view.getContext(), "Please fill the street field",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (building.getText().toString().isEmpty()) {
            Toasty.warning(view.getContext(), "Please fill the building field",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (apartment.getText().toString().isEmpty()) {
            Toasty.warning(view.getContext(), "Please fill the apartment field",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (phoneNumber.getText().toString().isEmpty()) {
            Toasty.warning(view.getContext(),
                    "Please fill the phoneNumber field",
                    Toast.LENGTH_SHORT, true).show();
            return;
        } else {
            db = FirebaseFirestore.getInstance();
            FirebaseUser firebaseUser =
                    FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                System.out.println("This is the user" + firebaseUser);
                DocumentReference docRef =
                        db.collection("Users").document(firebaseUser.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<
                        DocumentSnapshot>() {
                    @SuppressLint(
                            {
                                    "SetTextI18n",
                                    "CheckResult"}
                    )
                    @Override
                    public void
                    onSuccess(DocumentSnapshot
                                      documentSnapshot) {
                        user =
                                documentSnapshot.toObject
                                        (User.class);
                        HashMap<String,
                                String> newAddress =
                                new HashMap<>();
                        newAddress.put("city",
                                city.getText
                                        ().toString
                                        ());
                        newAddress.put("street",
                                street.getText
                                        ().toString
                                        ());
                        newAddress.put("building",
                                building.getText
                                        ().toString
                                        ());
                        newAddress.put("apartment",
                                apartment.getText
                                        ().toString
                                        ());
                        user.setAddress(newAddress);
                        db.
                                collection("Users").document
                                (firebaseUser.getUid
                                        ()).update("address",
                                newAddress,
                                "phoneNumber",
                                phoneNumber.getText
                                        ().toString());
                        Toasty.
                                success(view.getContext(),
                                        "Your information has been changed successfully",
                                        Toast.LENGTH_SHORT,
                                        true).show();
                    }
                });


            }
        }


    }
}
