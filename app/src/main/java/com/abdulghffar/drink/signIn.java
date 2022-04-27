package com.abdulghffar.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class signIn extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;
    private Button signInButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        setup();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);

                            Toasty.success(signIn.this,"You have been successfully logged in",Toast.LENGTH_SHORT,true).show();
                            startActivity(new Intent(signIn.this, mainActivity.class));
                        }
                        else{
                            Toasty.error(signIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();

                        }
                    }
                });
            }
        });

    }




    private void setup(){

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signinButton);



    }




    public void signUp(View view) {
        startActivity(new Intent(signIn.this,signUp.class));

    }
}