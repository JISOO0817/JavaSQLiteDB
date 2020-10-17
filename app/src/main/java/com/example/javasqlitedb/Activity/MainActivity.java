package com.example.javasqlitedb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.javasqlitedb.DB.Constants;
import com.example.javasqlitedb.DB.DBHelper;
import com.example.javasqlitedb.FoodAdapter;
import com.example.javasqlitedb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addFBtn;
    private RecyclerView recyclerView;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFBtn = findViewById(R.id.addFBtn);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        dbHelper = new DBHelper(this);

        loadData();

        addFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddActivity();
            }
        });
    }

    private void loadData() {
        FoodAdapter foodAdapter = new FoodAdapter(MainActivity.this, dbHelper.getAllData(Constants.C_ADD_STAMP + " DESC"));

        recyclerView.setAdapter(foodAdapter);
    }

    private void goAddActivity() {

        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


}