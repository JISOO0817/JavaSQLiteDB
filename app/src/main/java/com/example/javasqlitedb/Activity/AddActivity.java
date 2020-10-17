package com.example.javasqlitedb.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.javasqlitedb.DB.DBHelper;
import com.example.javasqlitedb.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddActivity extends AppCompatActivity {

    private ImageView photoIv;
    private EditText foodNameEt,foodPriceEt,foodDescriptionEt;
    private Button addBtn;

    private ActionBar actionBar;

    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 101;

    private static final int IMAGE_PICK_CAMERA = 200;
    private static final int IMAGE_PICK_GALLERY = 201;

    private String[] cameraPermission;
    private String[] storagePermission;

    Uri imageUrl;

    DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        actionBar = getSupportActionBar();

        actionBar.setTitle("음식 추가");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        photoIv = findViewById(R.id.photoIv);
        foodNameEt = findViewById(R.id.foodNameEt);
        foodPriceEt  = findViewById(R.id.foodPriceEt);
        foodDescriptionEt = findViewById(R.id.foodDescriptionEt);
        addBtn = findViewById(R.id.addBtn);

        dbHelper = new DBHelper(this);

        photoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });


    }

    private void addData() {

        String foodName = foodNameEt.getText().toString().trim();
        String foodPrice = foodPriceEt.getText().toString().trim();
        String foodDescription = foodDescriptionEt.getText().toString().trim();

        String timeStamp = ""+System.currentTimeMillis();

        if(TextUtils.isEmpty(foodName) || TextUtils.isEmpty(foodPrice) || TextUtils.isEmpty(foodDescription)){
            Toast.makeText(this, "빈칸이 없도록 채워주세요.", Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.insertData(foodName,""+imageUrl,foodPrice,foodDescription,timeStamp,timeStamp);
            startActivity(new Intent(AddActivity.this, MainActivity.class));
        }


    }

    private void ShowDialog() {

        String[] option = {"카메라","갤러리"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이미지 가져오기")
                .setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            if(checkCameraPermission()){
                                pickFromCamera();
                            }else{
                                requestCameraPermission();
                            }
                        }else{

                            if(checkStoragePermission()){
                                pickFromGallery();
                            }else{
                                requestStoragePermission();
                            }
                        }
                    }
                });
        builder.create().show();

    }

    private boolean checkCameraPermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result1&& result2;
    }

    private void pickFromCamera() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"image");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"description");

        imageUrl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUrl);
        startActivityForResult(intent,IMAGE_PICK_CAMERA);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode){

            case CAMERA_REQUEST:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else{
                        Toast.makeText(this, "카메라 권한이 필요해요~", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "갤러리 권한이 필요해요~", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //라이브러리로부터 이미지 크롭
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode == IMAGE_PICK_CAMERA){
                CropImage.activity(imageUrl)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }

            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK ){
                    Uri resultUri = result.getUri();
                    imageUrl = resultUri;
                    photoIv.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}