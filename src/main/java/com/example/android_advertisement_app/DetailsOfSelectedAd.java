package com.example.android_advertisement_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailsOfSelectedAd extends AppCompatActivity {

    private ImageView image;
    private TextView nameText;
    private TextView priceText;
    private TextView setloction;
    private TextView setcontact;
    private TextView editTextdescription;
   // private  TextView mImageEditText;

    private MyDBHelper dbHelper;
    private long receivedADId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_selected_ad);
        int user_id = getIntent().getExtras().getInt("USER_ID");

        image = (ImageView) findViewById(R.id.setimage);
        nameText = (TextView) findViewById(R.id.setname);
        priceText = (TextView) findViewById(R.id.setprice);
        setloction = (TextView) findViewById(R.id.setloction);
        setcontact = (TextView) findViewById(R.id.setcontact);
        editTextdescription = (TextView) findViewById(R.id.editTextdescription);

        dbHelper = new MyDBHelper(this);

        try {
            //get intent to get person id
            //receivedADId = getIntent().getLongExtra("USER_ID", 1);
            receivedADId = getIntent().getLongExtra("AD_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Advertisment queriedAdvertisment = dbHelper.getAD(receivedADId);

        //set field to this user data
        byte[] adimage = queriedAdvertisment.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(adimage,0, adimage.length);
        image.setImageBitmap(bitmap);

        nameText.setText(queriedAdvertisment.getName());
        priceText.setText(queriedAdvertisment.getPrice());
        setloction.setText(queriedAdvertisment.getLocation());
        setcontact.setText(queriedAdvertisment.getContactNo());
        editTextdescription.setText(queriedAdvertisment.getDescription());



        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
        btv.setSelectedItemId(R.id.showAd);

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

                    case R.id.aboutw:

                        //startActivity(new Intent(getApplicationContext(), Registration.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.showAd:// show ad all detail

                        overridePendingTransition(0, 0);
                        return true;



                }
                return false;


            }
        });




    }
}