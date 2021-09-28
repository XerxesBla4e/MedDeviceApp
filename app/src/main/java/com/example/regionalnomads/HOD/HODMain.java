package com.example.regionalnomads.HOD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import com.example.regionalnomads.CreateAccount;
import com.example.regionalnomads.LogSign.Signup;
import com.example.regionalnomads.R;

public class HODMain extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btncreate;
    Button btnview;
    Button btnadd;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_o_d_main);

        hooks();

        btnview.setOnClickListener(this);
        btncreate.setOnClickListener(this);
        btnadd.setOnClickListener(this);
    }

    private void hooks() {
        btncreate = findViewById(R.id.btncreate);
        btnview = findViewById(R.id.btnview2);
        btnadd = findViewById(R.id.btnadduser);
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.btnadduser:
                startActivity(new Intent(HODMain.this, CreateAccount.class));
                break;
            case R.id.btnview2:
                startActivity(new Intent(HODMain.this, HODPage.class));
                break;
            case R.id.btncreate:
                startActivity(new Intent(HODMain.this, Signup.class));
                break;
        }
    }
}