package com.example.mobileassignment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class UploadPageActivity extends AppCompatActivity {

    //for the location permissions; I chose to add users location everytime they add a image
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private EditText etxt_image_url;
    private EditText etxt_tag;
    private Button btn_save_image;
    private Button btn_back;
    private static ImageDatabase imageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);

        //get the database
        imageDatabase = Room.databaseBuilder(getApplicationContext(), ImageDatabase.class, "image-database").build();

        //variables
        etxt_image_url = findViewById(R.id.etxt_image_url_id);
        etxt_tag = findViewById(R.id.etxt_tag_id);
        btn_save_image = findViewById(R.id.btn_save_image_id);
        btn_back = findViewById(R.id.btn_back_id);

        //button for saving
        btn_save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImageData();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //better to have this as separate function due to the location permissions
    private void saveImageData() {
        String imageUrl = etxt_image_url.getText().toString().trim();
        String tag = etxt_tag.getText().toString().trim();

        if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(tag)) {
            //if got the permissions then get the location
            if (checkLocationPermission()) {
                getLocationAndSaveData(imageUrl, tag);
            }
            else {
                //ask for permission
                requestLocationPermission();
            }
        } else {
            Toast.makeText(this, "Both fields need to be filled", Toast.LENGTH_SHORT).show();
        }
    }

    //function which checks the permission
    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    //function that requests the permission
    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void getLocationAndSaveData(String imageUrl, String tag) {
        //check if location permission is granted
        if (checkLocationPermission()) {
            //start the FusedLocationProviderClient
            FusedLocationProviderClient location_client = LocationServices.getFusedLocationProviderClient(this);

            //get the location
            location_client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //check if the location is here
                    if (location != null) {
                        //create a object with the information
                        ImageData imageData = new ImageData();
                        imageData.setImage_url(imageUrl);
                        imageData.setImage_tag(tag);
                        imageData.setImage_latitude(location.getLatitude());
                        imageData.setImage_longitude(location.getLongitude());

                        //asynchronously add the new object to the database
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                imageDatabase.imageDao().insert(imageData);
                            }
                        });

                        //tidy up
                        etxt_image_url.getText().clear();
                        etxt_tag.getText().clear();

                        Toast.makeText(UploadPageActivity.this, "Item saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //function to give a result whether the image can be added or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            //if granted save the image
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageData();
            }
            //if not granted, notify the user
            else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
