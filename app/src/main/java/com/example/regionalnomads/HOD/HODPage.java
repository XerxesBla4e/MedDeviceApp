package com.example.regionalnomads.HOD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.regionalnomads.LogSign.Logn;
import com.example.regionalnomads.Model.UploadModel;
import com.example.regionalnomads.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HODPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_o_d_page);

        recyclerView = findViewById(R.id.machinerecycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UploadModel> options =
                new FirebaseRecyclerOptions.Builder<UploadModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Machines"), UploadModel.class)
                        .build();

        itemAdapter = new ItemAdapter(this, options);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        itemAdapter.stopListening();
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menumenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), Logn.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}