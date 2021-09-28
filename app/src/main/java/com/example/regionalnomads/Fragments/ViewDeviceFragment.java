package com.example.regionalnomads.Fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.regionalnomads.Adapter.MachineAdapter;
import com.example.regionalnomads.Model.MachinesModel;
import com.example.regionalnomads.R;
import com.example.regionalnomads.Users.DatabaseManger;


import java.sql.SQLDataException;
import java.util.ArrayList;

public class ViewDeviceFragment extends androidx.fragment.app.Fragment {

    private static final String TAG = "ViewDevice Fragment";

    RecyclerView recyclerView;
    MachineAdapter machineAdapter;
    private java.util.List<MachinesModel> machinesModelList;
    DatabaseManger databaseManager;

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.activity_retrieve_data, container, false);

        databaseManager = new DatabaseManger(getContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.machinerecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        java.util.List<MachinesModel> machinesModelList = new ArrayList<MachinesModel>();
        android.database.Cursor cursor = databaseManager.fetch();
        if (cursor.moveToFirst()) {
            do {
                machinesModelList.add(new MachinesModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)
                        , cursor.getString(7), cursor.getString(8), cursor.getString(9)));

            } while (cursor.moveToNext());

            machineAdapter = new MachineAdapter(getContext(), machinesModelList);
            recyclerView.setAdapter(machineAdapter);
            machineAdapter.notifyDataSetChanged();
        }

        return view;
    }

}
