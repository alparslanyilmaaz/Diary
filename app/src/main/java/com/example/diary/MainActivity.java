package com.example.diary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper db;
    ArrayList<MobileOs> mobileOs = new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseThemeChooser themes;
    String themeHolder;
    int backColor, headerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        db = new DatabaseHelper(this);

        themes = new DatabaseThemeChooser(this);
        Cursor themeColor = themes.getAllData();
        int counterThemes = themeColor.getCount();
        if(counterThemes != 0){
            if(themeColor.moveToFirst()){
                themeHolder = themeColor.getString(1);
            }
        }
        else{
            themes.insertData("L");
            themeHolder = "L";
        }

        if(themeHolder.equals("L")){
            backColor = ContextCompat.getColor(MainActivity.this, R.color.transparent);
            headerColor = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
        }
        else{
            backColor = ContextCompat.getColor(MainActivity.this, R.color.darkMode);
            headerColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setBackgroundColor(backColor);
        db = new DatabaseHelper(this);
        Cursor ren = db.getAllData();
        int a = ren.getCount();

        if(a == 0){
            Toast.makeText(MainActivity.this, "No input found. Please start to write.", Toast.LENGTH_LONG).show();
        }
        else{
            String id[] = new String[a];
            String date[] = new String[a];
            String time[] = new String[a];
            String value[] = new String[a];
            String pass[] = new String[a];
            String color[] = new String[a];
            String header[] = new String[a];
            String backColor[] = new String[a];
            int i=0;
            while (ren.moveToNext()){
                id[i] = ren.getString(0);
                time[i] = ren.getString(1);
                date[i] = ren.getString(2);
                value[i] = ren.getString(3);
                pass[i] = ren.getString(4);
                color[i] = ren.getString(5);
                header[i] = ren.getString(6);
                backColor[i] = ren.getString(7);
                i++;
            }
            for(int j=0 ; j<i ; j++){
                mobileOs.add(new MobileOs(time[j],date[j],value[j], id[j], pass[j], color[j], header[j], backColor[j]));
            }
        }
        final CustomAdapter customAdapter = new CustomAdapter(mobileOs , this);
        recyclerView.setAdapter(customAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Add.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_usage) {

        }
        else if (id == R.id.nav_settings) {
            Intent i = new Intent(MainActivity.this, Settings.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
