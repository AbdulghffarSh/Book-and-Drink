package com.abdulghffar.drink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class orderActivity extends AppCompatActivity {
    FirebaseFirestore db;
    TextView address, phoneNumber, orderStatus;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setup();
        String orderId = getIntent().getExtras().getString("order");
        orderStatus.setText("Order #" + orderId + "\n in transit");

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef =
                db.collection("Orders").document(orderId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<
                DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void
            onSuccess(DocumentSnapshot
                              documentSnapshot) {
                order order = documentSnapshot.toObject(order.class);
                ArrayList<String> addressList = new ArrayList<>(order.getUserAddress().values());
                address.setText(addressList.toString());
                phoneNumber.setText(order.getUserPhoneNumber());

            }


        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(orderActivity.this, mainActivity.class));

            }
        });


    }

    private void setup() {

        address = (TextView) findViewById(R.id.orderAddress);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        orderStatus = (TextView) findViewById(R.id.orderID);
        done = (Button) findViewById(R.id.done);

    }
}