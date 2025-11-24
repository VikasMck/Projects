package com.example.mobileassignment;

import androidx.room.*;

//declaring a database which is using dao, version 6 since kept changing
@Database(entities = {ImageData.class}, version = 6)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract ImageDaoClass imageDao();
}

/*
images if you want to test:

https://t4.ftcdn.net/jpg/05/51/71/61/360_F_551716108_yZSbx76w5A8ZX8Hjn9FhCKfaFp2hd2X9.jpg
https://wallpapers.com/images/hd/nice-colorful-flare-lights-rmx15m7a75ea0olp.jpg
https://wallpaperaccess.com/full/3155323.jpg
https://www.enjpg.com/img/2020/nice-15.jpg
https://img.freepik.com/premium-photo/gradient-abstract-constellation-background-wall-papers-cool-wallpapers-cute-wallpapers-background-wallpaper-phone-cool-backgrounds-cute-backgrounds-desktop-wallpaper_947865-1710.jpg
*/