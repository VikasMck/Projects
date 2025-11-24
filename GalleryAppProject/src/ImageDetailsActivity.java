package com.example.mobileassignment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.bumptech.glide.Glide;

public class ImageDetailsActivity extends AppCompatActivity {

    //declarations, I know most can be turned into local variables but I prefer keeping them here in case I make changes
    private ImageView image_view;
    private TextView txt_tag_view;
    private TextView txt_coords;
    private Button btn_update;
    private Button btn_delete;
    private Button btn_back;
    private Button btn_copy;

    //my dao
    private ImageDaoClass image_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        //getting xml ids
        image_view = findViewById(R.id.image_view_id);
        txt_tag_view = findViewById(R.id.txt_tag_view_id);
        txt_coords = findViewById(R.id.txt_coords_id);
        btn_update = findViewById(R.id.btn_update_id);
        btn_delete = findViewById(R.id.btn_delete_id);
        btn_back = findViewById(R.id.btn_back_id);
        btn_copy = findViewById(R.id.btn_copy_id);

        //initialising my db, it has some migration prevention as I had to change it multiple times
        ImageDatabase image_database = Room.databaseBuilder(getApplicationContext(), ImageDatabase.class, "image-database").fallbackToDestructiveMigration().build();

        //initialising clipboard thing
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //loading dao stuff into my db
        image_dao = image_database.imageDao();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("imageData")) {
            ImageData imageData = intent.getParcelableExtra("imageData");
            if (imageData != null) {
                //with glide load the image again
                Glide.with(this).load(imageData.getImage_url()).into(image_view);

                //the warning can be avoided if I made a local format of the string output in my res, but what I have no suffices
                String coordinatesText = String.format("Coordinates: %.6f, %.6f", imageData.getImage_latitude(), imageData.getImage_longitude());
                txt_coords.setText(coordinatesText);

                //get the tag
                txt_tag_view.setText(imageData.getImage_tag());

                //update button functions
                btn_update.setOnClickListener(v -> {
                    AlertDialog.Builder update_builder = new AlertDialog.Builder(this);
                    update_builder.setTitle("Update Image Tag");
                    update_builder.setMessage("Input a new tag:");

                    //search box with a hint
                    final EditText input = new EditText(this);
                    input.setHint(imageData.getImage_tag());
                    update_builder.setView(input);

                    update_builder.setPositiveButton("Yes", (dialog, which) -> {
                        //after yes is pressed, update the tag and store it into the db
                        String newTag = input.getText().toString().trim();
                        ImageData.updateTagInDatabase(imageData.getImage_id(), newTag, image_dao);

                        //simply update it to the new tag
                        txt_tag_view.setText(newTag);
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    });

                    //if no do nothing
                    update_builder.setNegativeButton("No", (dialog, which) -> {
                        dialog.cancel();
                    });

                    //show the dialog
                    update_builder.show();
                });

                //mostly same as update
                btn_delete.setOnClickListener(v -> {
                    AlertDialog.Builder delete_builder = new AlertDialog.Builder(this);
                    delete_builder.setTitle("Are you sure?");
                    delete_builder.setMessage("Do you wish to delete the image with tag: [ "+ imageData.getImage_tag() +" ]?");
                    delete_builder.setPositiveButton("Yes", (dialog, which) -> {
                        //if yes simply call a getter that does the delete part
                        ImageData.deleteImage(imageData.getImage_id(), image_dao);

                        //close the activity after deletion
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                    //if no then no
                    delete_builder.setNegativeButton("No", (dialog, which) -> {
                        dialog.cancel();
                    });
                    //show
                    delete_builder.show();
                });

                //copy button
                btn_copy.setOnClickListener(v -> {
                    //simple as calling a getter and storing into a variable
                    String image_url = imageData.getImage_url();
                    ClipData clip = ClipData.newPlainText("Image URL: ", image_url);
                    //make its primary
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
                });

                //btn back simply finish the intent
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }
    }
}
