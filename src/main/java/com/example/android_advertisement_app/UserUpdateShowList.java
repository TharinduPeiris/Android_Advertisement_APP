package com.example.android_advertisement_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserUpdateShowList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyDBHelper dbHelper;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_show_list);
        int user_id = getIntent().getExtras().getInt("USER_ID");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        mRecyclerView.setLayoutManager(gridLayoutManager);
        System.out.println("User ID Ad view : " + user_id);

        //populate recyclerview
        populaterecyclerView(user_id);


        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
        btv.setSelectedItemId(R.id.myads);

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.createadd:// create ad

                        Intent advertisementIntent = new Intent(getApplicationContext(), AddRecords.class);
                        advertisementIntent.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.ads_listw:// all users ads show

                        Intent advertisementIntent1 = new Intent(getApplicationContext(), MainActivity.class);
                        advertisementIntent1.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent1);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:

                        Intent intent = new Intent(UserUpdateShowList.this, Login.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.myads:// current login user ads
                        Intent advertisementIntent2 = new Intent(getApplicationContext(), UserUpdateShowList.class);
                        advertisementIntent2.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent2);
                        overridePendingTransition(0, 0);
                        return true;



                }
                return false;


            }
        });



    }

    //-------------update data show filter database

    private void populaterecyclerView(int filter){
        dbHelper = new MyDBHelper(this);
        adapter = new UserAdapter(dbHelper.peopleList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }


}