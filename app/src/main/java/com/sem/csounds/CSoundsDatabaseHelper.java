package com.sem.csounds;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class CSoundsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CSounds";
    private static final int DB_VERSION = 1;
    String Wilson = "Dr. Theresa Wilson";
    String Skiadas = "Dr. Haris Skiadas";
    String Wahl = "Dr. Barbara Wahl";
    String Collins = "Dr. John Collins";



    CSoundsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("CREATE TABLE PEOPLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "LAST_NAME TEXT, "
                + "NAME TEXT, "
                + "BIO TEXT, "
                + "IMAGE_ID INTEGER);");

        insertPeople(db, "Collins",
                Collins,
                "Collins Bio",
                R.drawable.collins);
        insertPeople(db, "Skiadas",
                Skiadas,
                "Skiadas Bio",
                R.drawable.skiadas);
        insertPeople(db, "Wahl",
                Wahl,
                "Wahl Bio",
                R.drawable.wahl);
        insertPeople(db, "Wilson",
                Wilson,
                "Wilson Bio",
                R.drawable.wilson);


        db.execSQL("CREATE TABLE SOUNDS ("
                + "pNAME TEXT, "
                + "QUOTE TEXT, "
                + "SOUND_ID_1 INTEGER PRIMARY KEY);");

        insertSounds(db, Wilson, "Do the coding practice!", R.raw.twilson1);
        insertSounds(db, Wilson, "I'm frowning at you!", R.raw.twilson2);
        insertSounds(db, Wilson, "Crazy kitties!", R.raw.twilson3);
        insertSounds(db, Wilson, "No one ever expects the Spanish Inquisition!", R.raw.twilson4);
        insertSounds(db, Wilson, "START EARLY!", R.raw.twilson5);

        insertSounds(db, Wahl, "Please, show your work.", R.raw.bwahl1);
        insertSounds(db, Wahl, "What is the job of the constructor?", R.raw.bwahl2);
        insertSounds(db, Wahl, "How do you know that's correct?", R.raw.bwahl3);
        insertSounds(db, Wahl, "Is there a helpful diagram you could make for this situation?", R.raw.bwahl4);
        insertSounds(db, Wahl, "You can always ask me for a hint or stop by my office hours!", R.raw.bwahl5);

        insertSounds(db, Skiadas, "For every complex problem, there is a simple solution...and it's always wrong.", R.raw.skiadas1);
        insertSounds(db, Skiadas, "I assume you've been doing the reading.", R.raw.skiadas2);
        insertSounds(db, Skiadas, "Don't get me started about C++.", R.raw.skiadas3);
        insertSounds(db, Skiadas, "Mutation is the root of all evil", R.raw.skiadas4);
        insertSounds(db, Skiadas, "Beware of bugs in the above code, I have only proved it correct, not tried it", R.raw.skiadas5);

        insertSounds(db, Collins, "I'm not John Collins...", R.raw.not);

    }

    private static void insertPeople(SQLiteDatabase db, String lastName, String name,
                                    String bio, int image_id) {
        ContentValues peopleValues = new ContentValues();
        peopleValues.put("LAST_NAME", lastName);
        peopleValues.put("NAME", name);
        peopleValues.put("BIO", bio);
        peopleValues.put("IMAGE_ID", image_id);
        db.insert("PEOPLE", null, peopleValues);
    }

    private static void insertSounds(SQLiteDatabase db, String name, String quote,
                                     int sound_id_1) {
        ContentValues soundValues = new ContentValues();
        soundValues.put("pNAME", name);
        soundValues.put("QUOTE", quote);
        soundValues.put("SOUND_ID_1", sound_id_1);
        db.insert("SOUNDS", null, soundValues);
    }
}
