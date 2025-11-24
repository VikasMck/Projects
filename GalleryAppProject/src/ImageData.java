package com.example.mobileassignment;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//creating the table
@Entity(tableName = "image_data")
public class ImageData implements Parcelable {

    //creating attributes for the table
    @PrimaryKey(autoGenerate = true)
    private int image_id;

    @ColumnInfo(name = "image_url")
    private String image_url;

    @ColumnInfo(name = "image_tag")
    private String image_tag;

    @ColumnInfo(name = "latitude")
    private double image_latitude;

    @ColumnInfo(name = "longitude")
    private double image_longitude;


    //empty constructor was required
    public ImageData() {
    }

    //all getters and setters for private attributes
    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_tag() {
        return image_tag;
    }

    public void setImage_tag(String image_tag) {
        this.image_tag = image_tag;
    }

    public double getImage_latitude() {
        return image_latitude;
    }

    public void setImage_latitude(double image_latitude) {
        this.image_latitude = image_latitude;
    }

    public double getImage_longitude() {
        return image_longitude;
    }

    public void setImage_longitude(double image_longitude) {
        this.image_longitude = image_longitude;
    }

    //full constructor
    protected ImageData(Parcel in) {
        image_id = in.readInt();
        image_url = in.readString();
        image_tag = in.readString();
        image_latitude = in.readDouble();
        image_longitude = in.readDouble();
    }

    //images did not work without such parcelable method so had to learn. This uses creator class which does all the work
    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    //must have function since implements parcelable otherwise will give an error
    @Override
    public int describeContents() {
        return 0;
    }

    //same here, without this images would not load in other classes
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image_id);
        dest.writeString(image_url);
        dest.writeString(image_tag);
        dest.writeDouble(image_latitude);
        dest.writeDouble(image_longitude);
    }

    //name of the function explains it, it uses threads -> {} annotation implements Runnable interface; same for delete
    public static void updateTagInDatabase(int image_id, String new_image_tag, ImageDaoClass imageDataDao) {
        new Thread(() -> {
            //update the tag in using the id
            imageDataDao.updateTag(image_id, new_image_tag);
        }).start();
    }

    //name says it
    public static void deleteImage(int image_id, ImageDaoClass imageDataDao) {
        new Thread(() -> {
            imageDataDao.deleteImage(image_id);
        }).start();
    }
}

