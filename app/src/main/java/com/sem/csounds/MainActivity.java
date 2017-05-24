package com.sem.csounds;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    };

    private ShareActionProvider shareActionProvider;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private SQLiteDatabase db;
    private Cursor cursor, cursorGrid;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //do DB operations for drawer layout
        try{
            SQLiteOpenHelper CSoundsDatabaseHelper = new CSoundsDatabaseHelper(this);
            db = CSoundsDatabaseHelper.getReadableDatabase();

            cursor = db.query("PEOPLE",
                    new String[]{"_id", "LAST_NAME", "NAME"},
                    null, null, null, null,
                    "LAST_NAME ASC");
            cursorGrid = db.query("PEOPLE",
                    new String[]{"_id", "LAST_NAME", "NAME", "IMAGE_ID"},
                    null, null, null, null,
                    "LAST_NAME ASC");
            setupRecyclerView(cursorGrid);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);

            //listSites.setAdapter(listAdapter);
            drawerList = (ListView) findViewById(R.id.drawer);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerList.setAdapter(listAdapter);
            drawerList.setOnItemClickListener(new DrawerItemClickListener());


            //Create the ActionBarDrawerToggle
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.string.open_drawer, R.string.close_drawer) {
                //Called when a drawer has settled in a completely closed state
                @Override
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    invalidateOptionsMenu();
                }
                //Called when a drawer has settled in a completely open state.
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                }
            };

            drawerLayout.setDrawerListener(drawerToggle);
            getSupportActionBar().setLogo(R.drawable.csounds_logo_cond);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            getFragmentManager().addOnBackStackChangedListener(
                    new FragmentManager.OnBackStackChangedListener() {
                        public void onBackStackChanged() {
                            FragmentManager fragMan = getFragmentManager();
                            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");

                            //if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                            //}
                            drawerList.setItemChecked(currentPosition, true);
                        }
                    }
            );

        } catch(SQLiteException error) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void setupRecyclerView(Cursor cursor){
        ArrayList<Professor> professorArrayList = new ArrayList<>();

        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            professorArrayList.add(new Professor(cursor));
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        int colNumber = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, colNumber);

        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, professorArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private void selectItem(int position) {
        // update the main content by replacing fragments
        currentPosition = position;

        Intent intent = new Intent(MainActivity.this, ProfessorActivity.class);
        intent.putExtra(ProfessorActivity.ITEM, (int) currentPosition);
        startActivity(intent);

        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0, 9, 0, "ABOUT").setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else{
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void aboutButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}

