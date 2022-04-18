package com.abdulghffar.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class signUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private EditText fullName;
    private EditText phoneNumber;
    private RadioGroup radioGroup;


    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        setup();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });
    }


    private void registerUser() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String rePasswordText = rePassword.getText().toString();
        String fullNameText = fullName.getText().toString();
        String phoneNumberText = phoneNumber.getText().toString();

        //check if information is correct
        //E-mail
        if (emailText.isEmpty()) {
            Toasty.warning(signUp.this, "Please enter your E-mail", Toast.LENGTH_SHORT, true).show();
            return;
        }

        //Password
        if (passwordText.isEmpty()) {
            Toasty.warning(signUp.this, "Please enter your Password", Toast.LENGTH_SHORT, true).show();
            return;
        }

        if (rePasswordText.isEmpty()) {
            Toasty.warning(signUp.this, "Please re-enter your Password ", Toast.LENGTH_SHORT, true).show();
            return;
        }

        if (!passwordText.equals(rePasswordText)) {
            Toasty.error(signUp.this, "The two Passwords do not match", Toast.LENGTH_SHORT, true).show();
            return;
        }

        //Name
        if (fullNameText.isEmpty()) {
            Toasty.warning(signUp.this, "Please enter your name", Toast.LENGTH_SHORT, true).show();
            return;
        }


        //Phone Number
        if (phoneNumberText.isEmpty()) {
            Toasty.warning(signUp.this, "Please enter your Phone Number", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toasty.warning(signUp.this, "Please choose your gender", Toast.LENGTH_SHORT, true).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toasty.success(signUp.this, "Successfully signed up", Toast.LENGTH_SHORT, true).show();
                    FirebaseUser newUser = mAuth.getCurrentUser();
                    writeNewUser(newUser);
                    Intent intent = new Intent(signUp.this, signIn.class);
                    startActivity(intent);
                    finish();
                    return;

                } else {
                    // If sign in fails, display a message to the user.
                    Toasty.error(signUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }

            }
        });

    }

    private void writeNewUser(FirebaseUser newUser) {
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        String genderText = "";
        if (checkedRadioButtonId == R.id.maleButton) {
            genderText = "Male";
        } else {
            genderText = "Female";
            System.out.println(checkedRadioButtonId);
        }
        assert newUser != null;
        User user = new User(newUser.getUid(), email.getText().toString(), fullName.getText().toString(), phoneNumber.getText().toString(), genderText);
        myRef.child(user.getuID());
        myRef = mDatabase.getReference("users/" + user.getuID());
        myRef.child("userEmail").setValue(user.getEmail());
        myRef.child("userGender").setValue(genderText);
        myRef.child("userName").setValue(user.getFullName());
        myRef.child("userNumber").setValue(user.getPhoneNumber());
    }


    private void setup() {
        email = (EditText) findViewById(R.id.email);

        password = (EditText) findViewById(R.id.password);

        rePassword = (EditText) findViewById(R.id.rePassword);
        fullName = (EditText) findViewById(R.id.fullName);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);


        signUpButton = (Button) findViewById(R.id.signupButton);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }
}