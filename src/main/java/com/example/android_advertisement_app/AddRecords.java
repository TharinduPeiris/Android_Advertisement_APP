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

public class AddRecords extends AppCompatActivity {

    private MyDBHelper dbHelper;
    final int REQUEST_CODE_GALLERY = 999;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private EditText contactnoEditText;
    private EditText locationEditText;
    ImageView imageView;

    Button btnChoose;
    private Button addBtn;
    private Button btnlist;
    //private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);
        int user_id = getIntent().getExtras().getInt("USER_ID");
        System.out.println("Add record: "+ user_id);

        nameEditText = (EditText) findViewById(R.id.editTextname);
        priceEditText = (EditText) findViewById(R.id.editTextprice);
        descriptionEditText = (EditText) findViewById(R.id.editTextdescription);
        imageView = (ImageView) findViewById(R.id.imageView1);
        contactnoEditText = (EditText) findViewById(R.id.editTextcontactno);
        locationEditText = (EditText) findViewById(R.id.editTextlocation);


        btnChoose = (Button) findViewById(R.id.btnchoose);
        addBtn = (Button) findViewById(R.id.btnadd);
        btnlist = (Button) findViewById(R.id.btnlist);

        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
        btv.setSelectedItemId(R.id.createadd);

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.createadd: // create ad

                        Intent advertisementIntent = new Intent(getApplicationContext(),AddRecords.class);
                        advertisementIntent.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.ads_listw: // all users ads show

                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:

                        Intent intent = new Intent(AddRecords.this, Login.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.myads:// current login user ads

                        Intent advertisementIntent1 = new Intent(getApplicationContext(), UserUpdateShowList.class);
                        advertisementIntent1.putExtra("USER_ID", user_id);
                        startActivity(advertisementIntent1);
                        overridePendingTransition(0, 0);
                }
                return false;


            }
        });

        /**
         * Choose Button (image)
         **/
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddRecords.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY
                );

            }
        });

        /**
         * Add Button
         **/

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    saveAD(user_id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        /**
         * Ads List
         **/

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                  listpage(user_id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

    }

    /**
     * permission to access file loction
     **/

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
                imageView.setImageBitmap(bitmap);

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
     * save ad
     **/

    private void saveAD(int uid){
        int user_id = uid;
        System.out.println("save ad : " + uid);
        String name = nameEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String contactNo = contactnoEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        byte[] image =  imageViewToByte(imageView);

        dbHelper = new MyDBHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(price.isEmpty()){
            //error price is empty
            Toast.makeText(this, "You must enter an price", Toast.LENGTH_SHORT).show();
        }

        if(description.isEmpty()){
            //error description is empty
            Toast.makeText(this, "You must enter an description", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Advertisment advertisment = new Advertisment(name, price, description, image, contactNo, location, user_id);
        dbHelper.saveNewAD(advertisment);

        //finally redirect back home

        goBackHome(uid);

    }

    private void goBackHome(int user_id){

        Intent advertisementIntent = new Intent(getApplicationContext(),UserUpdateShowList.class);
        advertisementIntent.putExtra("USER_ID", user_id);
        startActivity(advertisementIntent);

    }


    private void listpage(int user_id) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("USER_ID", user_id);
        startActivity(intent);

    }


}