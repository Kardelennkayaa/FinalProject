package com.example.bitirmeprojesi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Journey extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    Button button;

    TextView textView1, textView2, textView3, textView4, textView5;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey);

        button = findViewById(R.id.button2);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        button.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(Journey.this
                    , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                getLocation();

            } else {

                ActivityCompat.requestPermissions(Journey.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

            }
        });

    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

            Location location = task.getResult();
            if (location != null) {

                try {

                    Geocoder geocoder = new Geocoder(Journey.this
                            , Locale.getDefault());

                    List<Address> addresses = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1
                    );

                    textView1.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                    + addresses.get(0).getLatitude()
                    ));

                    textView2.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                    + addresses.get(0).getLongitude()
                    ));

                    textView3.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Country Name :</b><br></font>"
                                    + addresses.get(0).getCountryName()
                    ));

                    textView4.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Locality :</b><br></font>"
                                    + addresses.get(0).getLocality()
                    ));

                    textView5.setText(Html.fromHtml(
                            "<font color='#6200EE'><b>Address :</b><br></font>"
                                    + addresses.get(0).getAddressLine(0)
                    ));


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

    }
}
