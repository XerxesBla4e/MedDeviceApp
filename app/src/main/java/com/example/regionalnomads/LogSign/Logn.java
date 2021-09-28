package com.example.regionalnomads.LogSign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regionalnomads.HOD.HODMain;
import com.example.regionalnomads.MainActivity;
import com.example.regionalnomads.Model.HODModel;
import com.example.regionalnomads.Model.UserModel;
import com.example.regionalnomads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Logn extends AppCompatActivity implements android.view.View.OnClickListener {

    private EditText etemail, etpassword;
    private String semail, spass, hodname, hodpass;
    private String pass, email, status;
    private Button btn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private android.widget.TextView frgtpass, signup;
    private ImageView backbtn;
    DatabaseReference firebaseDatabase;
    DatabaseReference retrievehod;
    private static final String TAG = "Machin Fragment";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static final String shared_db = "log_data";
    public static final String email_key = "Email";
    public static final String pass_key = "Password";

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logn);

        hooks();

        String e = sharedPreferences.getString(email_key, "");
        String p = sharedPreferences.getString(pass_key, "");

        etemail.setText(e);
        etpassword.setText(p);

        btn.setOnClickListener(this);
        //frgtpass.setOnClickListener(this);
        //  signup.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        backbtn = findViewById(R.id.back_presssed);

        backbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Logn.super.onBackPressed();
            }
        });
    }

    public void hooks() {
        etemail = findViewById(R.id.uemail);
        etpassword = findViewById(R.id.upass);
        btn = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressB);
        //  frgtpass = findViewById(R.id.fgtps);
        //  signup = findViewById(R.id.sgnup1);
        sharedPreferences = getSharedPreferences(shared_db, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        retrievehod = FirebaseDatabase.getInstance().getReference("User");
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.button:
                userLogin();
                break;
                /*
            case R.id.sgnup1:
                startActivity(new Intent(Logn.this, Signup.class));
                break;
            case R.id.fgtps:
                startActivity(new Intent(Logn.this, forgotpass.class));
                break;*/
        }
    }

    private void userLogin() {
        String semail = etemail.getText().toString().trim();
        String spass = etpassword.getText().toString().trim();

        if (semail.isEmpty()) {
            etemail.setError("Email is required!");
            etemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            etemail.setError("Please Enter a valid Email!");
            etemail.requestFocus();
            return;
        }

        if (spass.isEmpty()) {
            etpassword.setError("Password is required!");
            etpassword.requestFocus();
            return;
        }

        if (spass.length() < 6) {
            etpassword.setError("Min password is 6 characters!");
            etpassword.requestFocus();
            return;
        }

        editor.putString(email_key, semail);
        editor.putString(pass_key, spass);
        editor.apply();

        progressBar.setVisibility(android.view.View.VISIBLE);

        retrievehod.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    HODModel hodModel = postSnapShot.getValue(HODModel.class);
                    status = hodModel.getStatus();
                    hodname = hodModel.getName();
                    hodpass = hodModel.getPass();

                    if (semail.equals(hodname) && spass.equals(hodpass)) {
                        mAuth.signInWithEmailAndPassword(semail, spass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            if (user.isEmailVerified()) {
                                                startActivity(new Intent(Logn.this, HODMain.class));
                                                progressBar.setVisibility(android.view.View.GONE);
                                            } else {
                                                user.sendEmailVerification();
                                                Toast.makeText(Logn.this, "Check Your Email To verify Account", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(Logn.this, "Failed to Login! Please Check your credentials", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(android.view.View.GONE);
                                        }
                                    }
                                });

                    }
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });


        if (semail.equals("Admin@gmail.com") && spass.equals("Admin123")) {
            startActivity(new Intent(Logn.this, HODMain.class));
            progressBar.setVisibility(android.view.View.GONE);
        } else {
            firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                        UserModel userModel = postSnapShot.getValue(UserModel.class);
                        email = userModel.getEmail();
                        pass = userModel.getPassword();

                        if (semail.equals(email) && spass.equals(pass)) {
                            Intent i = new Intent(Logn.this, MainActivity.class);
                            i.putExtra("email",email);
                            startActivity(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                }
            });

        }


        /*
        mAuth.signInWithEmailAndPassword(semail, spass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user.isEmailVerified()) {
                                startActivity(new Intent(Logn.this, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(Logn.this, "Check Your Email To verify Account", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Logn.this, "Failed to Login! Please Check your credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });*/
    }
}