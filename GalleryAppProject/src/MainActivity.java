package com.example.mobileassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    //variables
    private static ImageDatabase image_database;
    Button btn_main_images;
    Button btn_main_upload;
    Button btn_main_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise the db
        image_database = Room.databaseBuilder(getApplicationContext(), ImageDatabase.class, "image-database").fallbackToDestructiveMigration().build();

        //get ids for the buttons
        btn_main_images = findViewById(R.id.btn_main_images_id);
        btn_main_upload = findViewById(R.id.btn_main_upload_id);
        btn_main_exit = findViewById(R.id.btn_main_exit_id);

        //listener for going to images
        btn_main_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImagesPageActivity.class));
            }
        });

        //button for going to uploading an image, the syntax can be made using lambda, but I keep it different
        btn_main_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UploadPageActivity.class));
            }
        });


        //button that simply closes the app
        btn_main_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public static ImageDatabase getAppDatabase() {
        return image_database;
    }

}
