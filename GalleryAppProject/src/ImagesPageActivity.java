package com.example.mobileassignment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ImagesPageActivity extends AppCompatActivity {

    //all declarations
    private RecyclerView recycler_view;
    private ImagesAdapter adapter_var;
    private List<ImageData> images_array;
    private TextView image_page_title;
    private EditText etxt_search;
    private Button btn_search;
    private Button btn_back;
    private static ImageDatabase image_database;

    //for the requirements
    private ActivityResultLauncher<Intent> imageDetailsLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get all the xml objects
        setContentView(R.layout.activity_images_page);
        image_page_title = findViewById(R.id.txt_image_page_title_id);
        btn_back = findViewById(R.id.btn_back_id);
        etxt_search = findViewById(R.id.etxt_search_id);
        btn_search = findViewById(R.id.btn_search_id);
        recycler_view = findViewById(R.id.recycler_view_id);

        //start the database on create
        image_database = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, "image-database").build();

        //back button
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));

        //create an array of images
        images_array = new ArrayList<>();
        //create a variable with another class that has all functions to manage the images
        adapter_var = new ImagesAdapter(images_array, this);
        recycler_view.setAdapter(adapter_var);

        //call the function
        adapter_var.setOnItemClickListener((position, imageData) -> {
            openImageDetailsActivity(imageData);
        });

        //its deprecated but seemed easier than using executor, works for my case to achieve asynchronous operation
        AsyncTask.execute(() -> {
            //initialise with current images
            images_array = image_database.imageDao().getAllImages();
            updateAdapter();

            //to see what and how stuff are stored in my db
            List<ImageData> allImages = image_database.imageDao().getAllImages();
            for (ImageData imageData : allImages) {
                Log.d("ImageData", "ID: " + imageData.getImage_id() + ", Tag: " + imageData.getImage_tag() + ", ImageUrl: " + imageData.getImage_url() + " " +  imageData.getImage_latitude());
            }
        });

        //functionality of the search button
        btn_search.setOnClickListener(v -> {
            //get the input of the text box
            String query = etxt_search.getText().toString().trim();
            //logic same as previous function, just needed an if statement to bring back everything when search is blank
            AsyncTask.execute(() -> {
                List<ImageData> filteredImages;
                //if not empty
                if (!query.isEmpty()) {
                    filteredImages = image_database.imageDao().getImagesByTag(query);
                } else {
                    filteredImages = image_database.imageDao().getAllImages();
                }
                images_array.clear();
                images_array.addAll(filteredImages);
                //this updates the recycler UI component
                runOnUiThread(this::updateAdapter);
            });
        });

        //this is for the requirement; create an instance of the API with the name result
        imageDetailsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            //check if the results are ok, if so get data to the result
            if (result.getResultCode() == RESULT_OK) {
                //create the intent
                Intent data = result.getData();
            }
        });

        loadImagesFromDatabase();
    }

    //not really needed for my case as there isn't many things going on, but for the sake of having it
    @Override
    protected void onPause() {
        super.onPause();
    }

    //on resume, when I go back to this page I want the page to be reloaded with latest information from the db
    @Override
    protected void onResume() {
        super.onResume();
        loadImagesFromDatabase();
    }

    //main function for loading the info
    private void loadImagesFromDatabase() {
        //used this before; start a asynchronous task and call a dao query
        AsyncTask.execute(() -> {
            List<ImageData> allImages = image_database.imageDao().getAllImages();
            //after that is done refresh the UI
            runOnUiThread(() -> {
                images_array.clear();
                images_array.addAll(allImages);
                updateAdapter();
            });
        });
    }

    private void updateAdapter() {
        runOnUiThread(() -> {
            //update the adapter using other class functions
            adapter_var.updateData(images_array);
            //update the view so its at the top
            recycler_view.scrollToPosition(0);
        });
    }

    //for the requirement
    private void openImageDetailsActivity(ImageData imageData) {
        //make a new intent and send all data to it
        Intent intent = new Intent(ImagesPageActivity.this, ImageDetailsActivity.class);
        intent.putExtra("imageData", imageData);

        //with ActivityResultLauncher launch the new intent
        imageDetailsLauncher.launch(intent);
    }


    //just to have everything
    @Override
    protected void onDestroy() {
        image_database.close();
        super.onDestroy();
    }
}

