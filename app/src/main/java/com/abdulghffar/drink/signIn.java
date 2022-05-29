package com.abdulghffar.drink;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class signIn extends AppCompatActivity
{
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button signInButton;

    @Override protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance ();
        setup ();


        signInButton.setOnClickListener (new View.OnClickListener ()
                                         {
                                             @Override public void onClick (View v)
                                             {
                                                 String passwordText =
                                                         password.getText ().toString ();
                                                 String emailText =
                                                         email.getText ().toString ();
                                                 if (emailText.isEmpty ())
                                                 {
                                                     Toasty.warning (signIn.this,
                                                             "Please enter your Email",
                                                             Toast.LENGTH_SHORT,
                                                             true).show (); return;}
                                                 if (passwordText.isEmpty ())
                                                 {
                                                     Toasty.warning (signIn.this,
                                                             "Please enter your Password",
                                                             Toast.LENGTH_SHORT,
                                                             true).show (); return;}
                                                 else
                                                 {
                                                     mAuth.
                                                             signInWithEmailAndPassword (email.getText
                                                                             ().toString
                                                                             (),
                                                                     password.getText
                                                                             ().toString
                                                                             ()).addOnCompleteListener
                                                             (new OnCompleteListener < AuthResult > ()
                                                              {
                                                                  @Override
                                                                  public void
                                                                  onComplete
                                                                          (@NonNull Task < AuthResult > task)
                                                                  {
                                                                      progressBar.setVisibility
                                                                              (View.VISIBLE);
                                                                      if (task.isSuccessful ())
                                                                      {
                                                                          progressBar.setVisibility
                                                                                  (View.GONE);
                                                                          Toasty.success (signIn.this,
                                                                                  "You have been successfully logged in",
                                                                                  Toast.LENGTH_SHORT,
                                                                                  true).show ();
                                                                          startActivity (new
                                                                                  Intent
                                                                                  (signIn.this,
                                                                                          mainActivity.class));}
                                                                      else
                                                                      {
                                                                          Toasty.error (signIn.this,
                                                                                  task.getException
                                                                                          ().getMessage
                                                                                          (),
                                                                                  Toast.LENGTH_SHORT,
                                                                                  true).show ();}
                                                                  }
                                                              }
                                                             );}
                                             }
                                         }
        );

    }


    private void setup ()
    {

        progressBar = (ProgressBar) findViewById (R.id.progressBar);
        email = (EditText) findViewById (R.id.email);
        password = (EditText) findViewById (R.id.password);
        signInButton = (Button) findViewById (R.id.signinButton);

        TextView textView = (TextView) findViewById (R.id.singUp);
        String text = "Don't Have an Account? Sign up";
        SpannableString ss = new SpannableString (text);
        ForegroundColorSpan color1 =
                new ForegroundColorSpan (Color.rgb (10, 38, 80));
        ForegroundColorSpan color2 =
                new ForegroundColorSpan (Color.rgb (0, 218, 248));
        ss.setSpan (color1, 1, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan (color2, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText (ss);


        try
        {

            if (this.getIntent ().getExtras ().
                    getString ("logout").equals ("logout"))
            {

                logout ();

            }

        }
        catch (Exception e)
        {
            Log.e ("Error: ", e.getMessage ());
        }

    }


    public void signUp (View view)
    {
        startActivity (new Intent (signIn.this, signUp.class));

    }

    public void logout ()
    {

        FirebaseAuth.getInstance ().signOut ();
        Toasty.success (signIn.this, "You have successfully logged out",
                Toast.LENGTH_LONG, true).show ();


    }
}
