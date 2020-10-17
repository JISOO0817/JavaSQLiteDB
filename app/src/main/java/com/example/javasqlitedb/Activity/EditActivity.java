package com.example.javasqlitedb.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.javasqlitedb.DB.DBHelper;
import com.example.javasqlitedb.R;
import com.squareup.picasso.Picasso;

public class EditActivity extends AppCompatActivity {

    ImageView photoIv;
    EditText foodNameEt,foodPriceEt,foodDescriptionEt;
    Button updateBtn,deleteBtn;
    private DBHelper dbHelper;
    private String id,name,image,price,description,addTimeStamp,updateTimeStamp;
    private Uri imageUri;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        actionBar = getSupportActionBar();
        actionBar.setTitle("음식 수정");

        dbHelper = new DBHelper(this);
        photoIv = findViewById(R.id.photoIv);
        foodNameEt = findViewById(R.id.foodNameEt);
        foodPriceEt = findViewById(R.id.foodPriceEt);
        foodDescriptionEt = findViewById(R.id.foodDescriptionEt);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        price = intent.getStringExtra("PRICE");
        image = intent.getStringExtra("IMAGE");
        description = intent.getStringExtra("DESCRIPTION");
        addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
        updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");

        foodNameEt.setText(name);
        foodDescriptionEt.setText(description);
        foodPriceEt.setText(price);
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(photoIv);
        }catch (Exception e){
            photoIv.setImageResource(R.drawable.ic_launcher_background);
        }



        
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();

            }
        });
    }

    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제");
        builder.setMessage("삭제하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.delete(id);
                finish();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();

    }

    private void update() {

    }
    
}