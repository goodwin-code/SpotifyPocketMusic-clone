package com.example.spotifylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email1, pass1;
    Button loginbtn;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email1 = (EditText) findViewById(R.id.entermail);
        pass1 = (EditText) findViewById(R.id.password);
        fauth =FirebaseAuth.getInstance();
        loginbtn = (Button) findViewById(R.id.Verifymail2);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email1.getText().toString().trim();
                String Password = pass1.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email1.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    pass1.setError("Password is required");
                    return;
                }
                if (Password.length() < 8) {
                    pass1.setError("Password must be greater >=8 characters");
                    return;
                }
                fauth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
}