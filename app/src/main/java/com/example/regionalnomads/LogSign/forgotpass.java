package com.example.regionalnomads.LogSign;

import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.regionalnomads.R;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.regionalnomads.Adapter.MachineAdapter;
import com.example.regionalnomads.Model.MachinesModel;
import com.example.regionalnomads.R;
import com.example.regionalnomads.Users.DatabaseManger;


public class forgotpass extends AppCompatActivity {

    private EditText emailet;
    private Button resetbtn;
    private ProgressBar pb;

    FirebaseAuth auth;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        hooks();

        resetbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                resetPassword();
            }
        });
    }

    public void hooks() {
        emailet = findViewById(R.id.edtem);
        resetbtn = findViewById(R.id.button2);
        pb = findViewById(R.id.progB);
        auth = FirebaseAuth.getInstance();
    }

    private void resetPassword() {
        String email = emailet.getText().toString().trim();

        if (email.isEmpty()) {
            emailet.setError("Email is Required");
            emailet.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailet.setError("Please Enter a valid Email!");
            emailet.requestFocus();
            return;
        }

        pb.setVisibility(android.view.View.VISIBLE);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotpass.this, "Check Your Email To Reset Your Password", Toast.LENGTH_LONG).show();
                            pb.setVisibility(android.view.View.GONE);
                        } else {
                            Toast.makeText(forgotpass.this, "Ooops! Something went wrong", Toast.LENGTH_LONG).show();
                            pb.setVisibility(android.view.View.GONE);
                        }
                    }
                });
    }
}