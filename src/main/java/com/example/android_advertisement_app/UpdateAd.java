package com.example.android_advertisement_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateAd extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY = 999;
    private EditText UpName;
    private EditText UpPrice;
    private EditText UpContactno;
    private EditText UpLocation;
    private EditText UpDescription;
    private Button UpdateBtn;
    ImageView UpImage;
    Button btnChoose;

    private MyDBHelper dbHelper;
    private long receivedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ad);
        int user_id = getIntent().getExtras().getInt("USER_ID");
        long ad_id = getIntent().getLongExtra("AD_ID", 1);
        UpName = (EditText)findViewById(R.id.editTextUpName);
        UpPrice = (EditText)findViewById(R.id.editTextUpPrice);
        UpContactno = (EditText)findViewById(R.id.editTextUpContactno);
        UpLocation = (EditText)findViewById(R.id.editTextUpLocation);
        UpDescription = (EditText)findViewById(R.id.editTextUpDescription);

        UpdateBtn = (Button)findViewById(R.id.btnUpdate);

        btnChoose = (Button) findViewById(R.id.btnchoose);
        UpImage = (ImageView) findViewById(R.id.UpdateImageView1);

        dbHelper = new MyDBHelper(this);

        /***populate user data before update***/
        Advertisment adv = dbHelper.getAD(ad_id);//--------------------
        //set field to this user data
        UpName.setText("nameee"+adv.getName());
        UpPrice.setText(adv.getPrice());
        UpContactno.setText(adv.getContactNo());
        UpLocation.setText(adv.getLocation());
        UpDescription.setText(adv.getDescription());

        byte[] adimage = adv.getImage();
        System.out.println("ad image 1: " + adimage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(adimage,0, adimage.length);
        UpImage.setImageBitmap(bitmap);

        /**
         * choose button
         **/
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(UpdateAd.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY
                );

            }
        });

        /**
         * update button
         **/

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                System.out.println("AD ID : " + ad_id + " USER ID : " + adv.getUser_id());
                updateAd(ad_id, adv.getUser_id());
            }
        });

        // ---- bottom menu----

        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
        btv.setSelectedItemId(R.id.updateadd);

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {


                    case R.id.createadd: // create ads

                        Intent advertisementIntent = new Intent(getApplicationContext(), AddRecords.class);
                        advertisementIntent.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.ads_listw://all users ads

                        Intent advertisementIntent1 = new Intent(getApplicationContext(), MainActivity.class);
                        advertisementIntent1.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent1);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:

                        Intent intent = new Intent(UpdateAd.this, Login.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.updateadd:// update add page
                        overridePendingTransition(0, 0);
                        return true;



                }
                return false;


            }
        });


    }

 //permission to access file loction
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file loction!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                UpImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    /**
     * update ad
     **/
    private void updateAd(long ad_id, int user_id){

        System.out.println("Up ad : " + user_id);
        String name = UpName.getText().toString().trim();
        String price = UpPrice.getText().toString().trim();
        String contactNo = UpContactno.getText().toString().trim();
        String location = UpLocation.getText().toString().trim();
        String description = UpDescription.getText().toString().trim();

        byte[] image =  imageViewToByte(UpImage);
        System.out.println("ad image 2: " + image);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(price.isEmpty()){
            //error price is empty
            Toast.makeText(this, "You must enter  price", Toast.LENGTH_SHORT).show();
        }

        if(contactNo.isEmpty()){
            //error contactNo is empty
            Toast.makeText(this, "You must enter contactNo", Toast.LENGTH_SHORT).show();
        }

        if(location.isEmpty()){
            //error location is empty
            Toast.makeText(this, "You must enter location", Toast.LENGTH_SHORT).show();
        }

        if(description.isEmpty()){
            //error description is empty
            Toast.makeText(this, "You must enter description", Toast.LENGTH_SHORT).show();
        }

        //create updated person
        Advertisment updatedPerson = new Advertisment(name, price, description, image, contactNo, location, user_id);

        //call dbhelper update
        dbHelper.updateADRecord(ad_id, this, updatedPerson);

        //finally redirect back home
        homePage(user_id);

    }


    private void homePage(int user_id){
        System.out.println("Up home page : " + user_id);
        Intent advertisementIntent = new Intent(getApplicationContext(),UserUpdateShowList.class);
        advertisementIntent.putExtra("USER_ID", user_id);
        startActivity(advertisementIntent);

    }

}