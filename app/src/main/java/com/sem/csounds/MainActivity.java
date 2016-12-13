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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    Runnable update;

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
    private Cursor cursor;
    private ListView drawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        final int[] images = new int[] {R.drawable.group_picture, R.drawable.maggie_and_slaton,
                R.drawable.maggie_surprised, R.drawable.slatons_back};
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this, images);
        mViewPager.setAdapter(adapterView);

        handler = new Handler();

        update = new Runnable() {
            @Override
            public void run() {

                if(mViewPager.getCurrentItem() == images.length - 1){
                    mViewPager.setCurrentItem(0);
                }
                else{
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);

        try{
            SQLiteOpenHelper CSoundsDatabaseHelper = new CSoundsDatabaseHelper(this);
            db = CSoundsDatabaseHelper.getReadableDatabase();

            cursor = db.query("PEOPLE",
                    new String[]{"_id", "LAST_NAME", "NAME"},
                    null, null, null, null,
                    "LAST_NAME ASC");

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

    private void selectItem(int position) {
        // update the main content by replacing fragments
        currentPosition = position;

        Intent intent = new Intent(MainActivity.this, ProfessorActivity.class);
        intent.putExtra(ProfessorActivity.ITEM, (int) currentPosition);
        startActivity(intent);

        drawerLayout.closeDrawer(drawerList);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (handler!= null) {
            handler.removeCallbacks(update);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(update, 5000);
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
        return super.onOptionsItemSelected(item);
    }

    public void aboutButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}

