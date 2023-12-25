package com.example.meteo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView dht11TemperatureTextView, dht11HumidityTextView;
    private TextView dht22TemperatureTextView, dht22HumidityTextView;
    private TextView bmp280TemperatureTextView, bmp280HumidityTextView;

    private String TEMP_DHT11 = "/dht11/temp";
    private String HUM_DHT11 = "/dht11/hum";
    private String TEMP_DHT22 = "/dht22/temp";
    private String HUM_DHT22= "/dht22/hum";

    private String TEMP_BMP280 = "/bmp280/temp";
    private String HUM_BMP280 = "/bmp280/hum";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        dht11TemperatureTextView = findViewById(R.id.dht11_temperature);
        dht11HumidityTextView = findViewById(R.id.dht11_humidity);
        dht22TemperatureTextView = findViewById(R.id.dht22_temperature);
        dht22HumidityTextView = findViewById(R.id.dht22_humidity);
        bmp280TemperatureTextView = findViewById(R.id.bmp280_temperature);
        bmp280HumidityTextView = findViewById(R.id.bmp280_humidity);

        // Get a reference to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Add a listener to read data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear old data
                clearOldData();
                Log.d("po", "onDataChange:  " + dataSnapshot.getValue());
                Log.d("TAG", "onDataChange: dht11 temp " + dataSnapshot.child("/dht11/temp").getValue());
                Log.d("TAG", "onDataChange: dht22 " + dataSnapshot.child("/dht22/temp").getValue());
                Log.d("TAG", "onDataChange: " + dataSnapshot.child("/bmp280/temp"));
                dht11TemperatureTextView.setText(dataSnapshot.child(TEMP_DHT11).getValue().toString());
                dht11HumidityTextView.setText(dataSnapshot.child(HUM_DHT11).getValue().toString());

                dht22TemperatureTextView.setText(dataSnapshot.child(TEMP_DHT22).getValue().toString());
                dht22HumidityTextView.setText(dataSnapshot.child(HUM_DHT22).getValue().toString());
                bmp280TemperatureTextView.setText(dataSnapshot.child(TEMP_BMP280).getValue().toString());
               bmp280HumidityTextView.setText(dataSnapshot.child(HUM_BMP280).getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("Firebase", "Error reading from Firebase: " + databaseError.getMessage());
            }
        });
    }


    private void clearOldData() {
        dht11TemperatureTextView.setText("");
        dht11HumidityTextView.setText("");
        dht22TemperatureTextView.setText("");
        dht22HumidityTextView.setText("");
        bmp280TemperatureTextView.setText("");
        bmp280HumidityTextView.setText("");
    }
}
