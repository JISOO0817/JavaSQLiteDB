package com.example.javasqlitedb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javasqlitedb.Activity.EditActivity;
import com.example.javasqlitedb.DB.DBHelper;
import com.example.javasqlitedb.Model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {


    private Context context;
    private ArrayList<Model> foodData;
    Model model;

    DBHelper dbHelper;

    public FoodAdapter(Context context, ArrayList<Model> foodData) {
        this.context = context;
        this.foodData = foodData;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        Model model = foodData.get(position);

        final String id = model.getId();
        final String name = model.getName();
        final String price = model.getPrice();
        final String image = model.getImage();
        final String description = model.getDescription();
        final String addTimeStamp = model.getAddTimeStamp();
        final String updateTimeStamp = model.getUpdateTimeStamp();

        holder.foodNameTv.setText(name);

        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(holder.foodIv);
        }catch (Exception e){
            holder.foodIv.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("ID",id);
                        intent.putExtra("NAME",name);
                        intent.putExtra("PRICE",price);
                        intent.putExtra("DESCRIPTION",description);
                        intent.putExtra("IMAGE",image);
                        intent.putExtra("ADD_TIMESTAMP",addTimeStamp);
                        intent.putExtra("UPDATE_TIMESTAMP",updateTimeStamp);
                        context.startActivity(intent);

    }
        });

    }

    @Override
    public int getItemCount() {
        return foodData.size();
    }



    class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodIv;
        TextView foodNameTv;



        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodIv = itemView.findViewById(R.id.foodIv);
            foodNameTv = itemView.findViewById(R.id.foodNameTv);


        }
    }
}
