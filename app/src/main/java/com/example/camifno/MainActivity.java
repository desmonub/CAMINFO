package com.example.camifno;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseTempReference, databaseHumidityReference, databaseGasReference;

    // variable for Text view.
    private TextView temp, humidity, gas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseTempReference = firebaseDatabase.getReference("Temperature");
        databaseHumidityReference = firebaseDatabase.getReference("Humidity");
        databaseGasReference = firebaseDatabase.getReference("Gas");
//        databaseReference.orderByKey().limitToLast(1)

        // initializing our object class variable.
        temp = findViewById(R.id.t1);
        humidity = findViewById(R.id.t2);
        gas = findViewById(R.id.t3);

        // calling method
        // for getting data.
        getTemperaturedata();
        getHumiditydata();
        getGasdata();
    }

    private void getTemperaturedata() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
        try {
            databaseTempReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    String value = String.valueOf(map.values());
                    String [] values = value.split(",");
//                    temp.setText(values[values.length-1]);
                    temp.setText(value.split(",")[(value.split(",").length)-1].substring(0, value.split(",")[1].length() - 1));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
        }
    }


    private void getHumiditydata() {
        databaseHumidityReference.addValueEventListener(new ValueEventListener() {
//        databaseHumidityReference.orderByKey().limitToLast(0).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String value = String.valueOf(map.values());
//                humidity.setText(value);
                humidity.setText(value.split(",")[1]);
//                humidity.setText(values[1].substring(0, values[1].length() - 2));
                humidity.setText(value.split(",")[1].substring(0, value.split(",")[1].length() - 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGasdata() {
        databaseGasReference.addValueEventListener(new ValueEventListener() {
//        databaseGasReference.orderByKey().limitToLast(0).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String value = String.valueOf(map.values());
//                gas.setText(value);
                String [] values = value.split(",");
                gas.setText(value.split(",")[1]);
//                gas.setText(values[values.length-1].substring(0, values[1].length() - 2));
                gas.setText(value.split(",")[1].substring(0, value.split(",")[1].length() - 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}