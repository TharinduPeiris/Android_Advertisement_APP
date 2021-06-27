package com.example.android_advertisement_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyDBHelper dbHelper;
    private AdvertismentAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<Advertisment> adList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adList = new ArrayList<>();
        System.out.println("adlist 1 : " + adList);
        adapter = new AdvertismentAdapter(adList,this);




        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //Advertisment adv = dbHelper.getAD(ad_id);
        int filter = getIntent().getExtras().getInt("USER_ID");

        //populate recyclerview
        populaterecyclerView(filter);


        int user_id = getIntent().getExtras().getInt("USER_ID");
        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
        btv.setSelectedItemId(R.id.ads_listw);

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {


                    case R.id.createadd: // create ad

                        Intent advertisementIntent1 = new Intent(getApplicationContext(), AddRecords.class);
                        advertisementIntent1.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent1);
                        //startActivity(new Intent(getApplicationContext(), AddRecords.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.ads_listw: // all users ads show
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.myads: // current login user ads

                        Intent advertisementIntent = new Intent(getApplicationContext(), UserUpdateShowList.class);
                        advertisementIntent.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void populaterecyclerView(int filter){
        System.out.println("Filter : "+filter);
        dbHelper = new MyDBHelper(this);
        adList.addAll(dbHelper.allAdsList());
        adapter = new AdvertismentAdapter(adList, this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        System.out.println("adlist 2 : " + adList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu3,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this,"Action View Expanded..",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this,"Action View Collapsad..",Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        MenuItem searchad = menu.findItem(R.id.action_search);
        searchad.setOnActionExpandListener(onActionExpandListener);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

       // String userInput = newText.toLowerCase();
        newText = newText.toLowerCase();
        List<Advertisment> newList = new ArrayList<>();

        for (Advertisment ad :adList){

            String name = ad.getName().toLowerCase();
            System.out.println("nameeeeeeeee"+name);

            if(name.contains(newText)){
                newList.add(ad);
            }
            }

        adapter.updatelist(newList);
        return true;
    }




}