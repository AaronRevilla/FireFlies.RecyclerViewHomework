package com.example.aaron.recyclerviewhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText txtEdad;
    EditText txtPhone;
    EditText txtImg;


    boolean flagIsUpdate = false;
    Long usrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        txtName = ((EditText) findViewById(R.id.userName));
        txtEdad = ((EditText) findViewById(R.id.userAge));
        txtPhone = ((EditText) findViewById(R.id.userPhone));
        txtEmail = ((EditText) findViewById(R.id.userEmail));
        txtImg = ((EditText) findViewById(R.id.userImg));

        Intent i = getIntent();
        flagIsUpdate = i.getBooleanExtra("isUpdate", false);

        if(flagIsUpdate){
//            txtName.setText(i.getStringExtra("nameUser"));
//            txtEdad.setText(i.getIntExtra("ageUser",0) + "");
//            txtPhone.setText(i.getStringExtra("phoneUser"));
//            usrId = i.getLongExtra("idUser", 0);
        }


    }


    public void add(View view) {

        Intent i = new Intent();
        i.putExtra("nameUser", txtName.getText().toString());
        i.putExtra("ageUser",  txtEdad.getText().toString());
        i.putExtra("phoneUser",  txtPhone.getText().toString());
        i.putExtra("emailUser", txtEmail.getText().toString());
        i.putExtra("imgUser", txtImg.getText().toString());

        //i.putExtra("idUser",  usrId);
        setResult(RESULT_OK, i);
        finish();
    }


}
