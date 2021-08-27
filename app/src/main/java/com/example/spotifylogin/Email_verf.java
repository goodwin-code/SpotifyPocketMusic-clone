package com.example.spotifylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Email_verf extends AppCompatActivity {

    EditText email, Pass;
    Button b1,errbtn;
    TextView close;
    FirebaseAuth fauth;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verf);
        email = (EditText) findViewById(R.id.gmail1);
        b1 = (Button) findViewById(R.id.Verifymail);
        Pass = (EditText) findViewById(R.id.password);
        errbtn= (Button) findViewById(R.id.Gotologin);
        dialog = new Dialog(this);
        close = (TextView) findViewById(R.id.Closebtn);


        fauth = FirebaseAuth.getInstance();
        if (fauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Password = Pass.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    Pass.setError("Password is required");
                    return;
                }
                if (Password.length() < 8) {
                    Pass.setError("Password must be >=8 character");
                    return;
                }
                fauth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Email_verf.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            openVerifyDialog();
                        }
                    }
                });

            }

            private void openVerifyDialog() {
                dialog.setContentView(R.layout.email_error);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView errobtn = dialog.findViewById(R.id.Closebtn);
                Button goback = dialog.findViewById(R.id.Gotologin);
                goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Email_verf.this, Login.class);
                        startActivity(i);
                    }
                });
                errobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }



        });

        
    }
}