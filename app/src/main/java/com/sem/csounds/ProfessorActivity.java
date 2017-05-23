package com.sem.csounds;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {

    public static final String ITEM = "item";
    public static final String PROFESSSOR = "professor";
    int item;
    String professor;
    ListView listView;
    Cursor cursor;
    SQLiteDatabase db;
    MediaPlayer player;
    ArrayList<String> quoteList;

    @Override protected void onStop(){
        super.onStop();
        cursor.close();
        db.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        listView = (ListView)findViewById(R.id.sound_list);
        try{
            item = (Integer)getIntent().getExtras().get(ITEM);
            professor = (String)getIntent().getExtras().get(PROFESSSOR);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        String profName = professor;

        try{
            SQLiteOpenHelper CSoundsDatabaseHelper = new CSoundsDatabaseHelper(this);
            db = CSoundsDatabaseHelper.getReadableDatabase();

            if(item == 0) {
                profName = "Dr. John Collins";
            }
            else if(item == 1) {
                profName = "Dr. Haris Skiadas";
            }
            else if(item == 2) {
                profName = "Dr. Barbara Wahl";
            }
            else if(item == 3) {
                profName = "Dr. Theresa Wilson";
            }
            getSupportActionBar().setTitle(profName);

            cursor = db.query ("PEOPLE " + "INNER JOIN SOUNDS ON PEOPLE.NAME = SOUNDS.pNAME",
                        new String[] {"_id", "NAME", "BIO", "IMAGE_ID", "QUOTE", "SOUND_ID_1"},
                                      "PEOPLE.NAME = ?",
                        new String[] {profName}, null, null, null);
            //Cursor cursor2 = db.rawQuery("SELECT * FROM PEOPLE " +
            //                          "INNER JOIN SOUNDS ON PEOPLE.NAME = SOUNDS.NAME " +
            //                        "WHERE NAME = " + profName, null);
            String[] col = {"QUOTE"};
            int[] to = {android.R.id.text1};

             if(cursor.moveToFirst()) {
                 SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_activated_1,
                         cursor, col, to, 0);
                 listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                 listView.setAdapter(simpleCursorAdapter);

                 Log.e("CURSOR INFO", TextUtils.join(", ", cursor.getColumnNames()));
                 String nameText = cursor.getString(1);
                 String bioText = cursor.getString(2);
                 int imageId = cursor.getInt(3);

                 //TextView name = (TextView) findViewById(R.id.name);
                 //name.setText(nameText);

                 //TextView bio = (TextView) findViewById(R.id.bio);
                 //bio.setText(bioText);

                 ImageView picture = (ImageView) findViewById(R.id.picture);
                 picture.setImageResource(imageId);

             }
            //cursor.close();
            //db.close();

        } catch(SQLiteException e) {
            Log.v("SQLiteException", "..........SQLITE_EXCEPTION..........");
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
            Log.e("DATABASE ERROR", e.toString());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cursor.moveToPosition(position);
                player = MediaPlayer.create(ProfessorActivity.this, cursor.getInt(5));
                player.start();

            }
        });
    }

    public void onClick(View v){
        player = MediaPlayer.create(ProfessorActivity.this, 0);
        player.start();

    }

    private ArrayList<String> queryToArrayList(Cursor cursor){
        ArrayList<String> quoteList = new ArrayList<String>();
        String quote;

        while(cursor.moveToNext()) {
            quote = cursor.getString(cursor.getColumnIndex("QUOTE"));
            quoteList.add(quote);

        }
        return quoteList;
    }
}
