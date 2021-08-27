package com.example.spotifylogin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_rec extends AppCompatActivity {
    PinView p2;
    Button b2;
    String phonenumber;
    FirebaseAuth mAuth;
    String otpid;
    TextView setsms, resend;
    TextView edtphn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_rec);
        phonenumber = getIntent().getStringExtra("mobile").toString();
        p2 = (PinView) findViewById(R.id.pinView);
        b2 = (Button) findViewById(R.id.NxtBtn2);
        resend = (TextView) findViewById(R.id.ResendOtp);
        mAuth = FirebaseAuth.getInstance();
        edtphn = (TextView) findViewById(R.id.Editphn);
        setsms = (TextView) findViewById(R.id.smstxt);
        setsms.setText("We sent an SMS with a 6-digit code to" + " " + phonenumber + ".");
        setsms.setTextAppearance(this, R.style.fontforsms);

        resendotp();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendotp();

            }
        });
        initiateotp();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateotp();
                if (p2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Blank field cannot be processed", Toast.LENGTH_LONG).show();
                } else if (p2.getText().toString().length() != 6) {
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, p2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
        edtphn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(otp_rec.this,phoneNumber.class);
                startActivity(i);
            }
        });
    }

    private void resendotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(otp_rec.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Signin Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

