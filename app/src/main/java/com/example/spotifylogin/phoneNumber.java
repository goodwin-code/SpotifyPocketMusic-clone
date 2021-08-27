package com.example.spotifylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class phoneNumber extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText phnno;
    Button nxtbtn,errbtn;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        phnno = (EditText) findViewById(R.id.phone_no);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phnno);
        nxtbtn = (Button) findViewById(R.id.NxtBtn);
        errbtn= (Button) findViewById(R.id.TryagainBtn);
        dialog=new Dialog(this);

        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phnno.getText().toString().isEmpty()){
                    phnno.setError("Enter phone number");
                    openEmptyDialog();
                }else if(phnno.getText().toString().length()==10){
                    Intent intent = new Intent(getApplicationContext(),otp_rec.class);
                    intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                    startActivity(intent);
                }else if(phnno.getText().toString().length()<10){
                    phnno.setError("Digits should not < 10");
                   openNumLessDialog();
                }else if(phnno.getText().toString().length()>10){
                    phnno.setError("Enter only 10 digits number");
                    openGreaterDialog();
                }

            }
        });
    }

    private void openGreaterDialog() {
        dialog.setContentView(R.layout.emptyerr_bg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button errobtn = dialog.findViewById(R.id.TryagainBtn);
        errobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openNumLessDialog() {
        dialog.setContentView(R.layout.emptyerr_bg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button errobtn = dialog.findViewById(R.id.TryagainBtn);
        errobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void openEmptyDialog() {
        dialog.setContentView(R.layout.emptyerr_bg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button errobtn = dialog.findViewById(R.id.TryagainBtn);
        errobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}