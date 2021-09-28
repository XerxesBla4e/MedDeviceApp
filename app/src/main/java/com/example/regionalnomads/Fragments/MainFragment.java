package com.example.regionalnomads.Fragments;

import android.content.SharedPreferences;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.regionalnomads.Model.UploadModel;
import com.example.regionalnomads.Model.UserModel;
import com.example.regionalnomads.R;
import com.example.regionalnomads.MainActivity;
import com.example.regionalnomads.Users.DatabaseHelper;
import com.example.regionalnomads.Users.DatabaseManger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLDataException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.regionalnomads.LogSign.Logn.email_key;

public class MainFragment extends androidx.fragment.app.Fragment {
    private static final String TAG = "Main Fragment";

    private Button device;
    private Button viewdevice;
    private Button upload;
    // private Button hod;
    DatabaseManger databaseManger;
    DatabaseReference databaseReference;

    FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private String email;
    private String userName;
    private String HealthFacilty;
    private String HealthFlevel;


    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.mainfrag, container, false);

        /*
        reference = FirebaseDatabase.getInstance().getReference("Users");
        Query client_location = reference.orderByChild("email").equalTo(email);


        client_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    UploadModel uploadModel = postSnapShot.getValue(UploadModel.class);
                    userName = uploadModel.getUname();
                    HealthFacilty = uploadModel.getHealthfcty();
                    HealthFlevel = uploadModel.getHealthfctylvl();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                UserModel userProfile = snapshot.getValue(UserModel.class);

                if (userProfile != null) {
                    userName = userProfile.getName();
                    HealthFacilty = userProfile.getHealth_Facility();
                    HealthFlevel = userProfile.getHealthFacility_Level();
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });*/

        databaseManger = new DatabaseManger(getContext());

        try {
            databaseManger.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Machines");
        device = (Button) view.findViewById(R.id.btncreate);
        viewdevice = (Button) view.findViewById(R.id.btnview);
        upload = (Button) view.findViewById(R.id.btnadduser);
        /*
        hod = (Button) view.findViewById(R.id.btnview2);

        hod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HODPage.class);
                startActivity(intent);
            }
        });*/

        device.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                ((MainActivity) getActivity()).setViewPager(1);
            }
        });

        viewdevice.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                ((MainActivity) getActivity()).setViewPager(2);
            }
        });

        upload.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                java.util.List<UploadModel> uploadModelList = new ArrayList<UploadModel>();
                android.database.Cursor cursor = databaseManger.fetch();
                if (cursor.moveToFirst()) {
                    do {

                        String current_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CURRENT_DATE));
                        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                        String serialno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SERIAL_NUMBER));
                        String modelno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MODEL_NUMBER));
                        String manufacturer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MANUFACTURER));
                        String department = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEPARTMENT));
                        String machinestate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MACHINE_STATE));
                        String lastservice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LAST_SERVICE));
                        String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENT));

                        uploadModelList.add(new UploadModel(current_date, name, serialno, modelno, manufacturer
                                , department, machinestate, lastservice, comment));

                    }
                    while (cursor.moveToNext());

                    if (uploadModelList.size() > 0) {
                        for (UploadModel o : uploadModelList) {
                            databaseReference.push().setValue(o);
                        }
                        Toast.makeText(getContext(), "Machines' Details Successfully Upload", Toast.LENGTH_SHORT).show();
                        //  databaseManager.delete();
                    }
                }
            }
        });

        return view;
    }
}
