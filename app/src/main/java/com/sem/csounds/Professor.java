package com.sem.csounds;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by Slaton on 5/23/2017.
 */

public class Professor {
    private String name, bio, quote;
    private int imageId, soundId;

    Professor(Cursor cursor){
        int nameI, bioI, imageIdI, soundIdI, quoteI;
        nameI = cursor.getColumnIndex("NAME");
//        bioI = cursor.getColumnIndex("BIO");
        imageIdI = cursor.getColumnIndex("IMAGE_ID");
        soundIdI = cursor.getColumnIndex("SOUND_ID_1");
//        quoteI = cursor.getColumnIndex("QOUTE");

        this.name = cursor.getString(nameI);
//        this.bio = cursor.getString(bioI);
//        this.quote = cursor.getString(quoteI);
        this.imageId = cursor.getInt(imageIdI);
//        this.soundId = cursor.getInt(soundIdI);
        Log.e("Professor", this.name);
    }

    public String getName(){
        return this.name;
    }

    public int getImageId(){
        return this.imageId;
    }



}
