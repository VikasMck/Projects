package com.example.mobileassignment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

//main Dao class
@Dao
public interface ImageDaoClass {

    @Insert
    void insert(ImageData imageData);

    //this is used for all images
    @Query("SELECT * FROM image_data")
    List<ImageData> getAllImages();

    //this is used for the search feature
    @Query("SELECT * FROM image_data WHERE image_tag LIKE :image_tag")
    List<ImageData> getImagesByTag(String image_tag);

    //query that updates using id
    @Query("UPDATE image_data SET image_tag = :new_tag WHERE image_id = :image_id")
    void updateTag(int image_id, String new_tag);

    //delete query
    @Query("DELETE FROM image_data WHERE image_id = :image_id")
    void deleteImage(int image_id);
}
